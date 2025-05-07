import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import Navbar from '../components/organisms/Navbar';
import Footer from '../components/organisms/Footer';
import ProductForm from '../components/organisms/ProductForm';
import { useAuth } from '../context/AuthContext';
import { productsAPI } from '../services/api';
import { Product, ProductFormData, ApiError } from '../types';

const ProductEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const { isAuthenticated, user } = useAuth();
  const navigate = useNavigate();

  // Fetch product data
  useEffect(() => {
    const fetchProduct = async () => {
      if (!id) return;

      try {
        setLoading(true);
        const data = await productsAPI.getProduct(id);
        setProduct(data);
        setError(null);
      } catch (err) {
        console.error('Error fetching product:', err);

        const apiError = err as ApiError;

        // If error is due to authentication, redirect to login
        if (apiError.response && apiError.response.status === 401) {
          setError('Please log in to edit products');
          setTimeout(() => {
            navigate('/login', { state: { from: `/products/edit/${id}` } });
          }, 2000);
        } else if (apiError.response && apiError.response.status === 404) {
          setError('Product not found');
        } else {
          setError('Failed to load product details. Please try again later.');
        }
      } finally {
        setLoading(false);
      }
    };

    // Check if user is authenticated
    if (!isAuthenticated) {
      setError('Please log in to edit products');
      setTimeout(() => {
        navigate('/login', { state: { from: `/products/edit/${id}` } });
      }, 2000);
    } else {
      fetchProduct();
    }
  }, [id, isAuthenticated, navigate]);

  const handleSubmit = async (productData: ProductFormData) => {
    if (!id) return;

    try {
      setSubmitting(true);
      setError(null);

      await productsAPI.updateProduct(id, productData);
      navigate(`/products/${id}`, { state: { message: 'Product updated successfully' } });
    } catch (err) {
      console.error('Error updating product:', err);

      const apiError = err as ApiError;

      if (apiError.response && apiError.response.status === 401) {
        setError('Please log in to edit products');
        setTimeout(() => {
          navigate('/login', { state: { from: `/products/edit/${id}` } });
        }, 2000);
      } else if (apiError.response && apiError.response.status === 400) {
        // Handle validation errors
        const errorMessage = apiError.response.data.message || 'Invalid product data';
        setError(`Validation error: ${errorMessage}`);
      } else if (apiError.response && apiError.response.status === 404) {
        setError('Product not found');
      } else {
        setError('Failed to update product. Please try again.');
      }
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="flex flex-col min-h-screen">
      <Navbar 
        isLoggedIn={isAuthenticated}
        username={user?.email}
      />

      <main className="flex-grow container mx-auto px-4 py-8">
        <div className="mb-6">
          <Link 
            to={`/products/${id}`} 
            className="text-blue-600 hover:text-blue-800 flex items-center"
          >
            <span className="mr-2">←</span> Back to Product
          </Link>
        </div>

        <h1 className="text-3xl font-bold text-gray-800 mb-8">Edit Product</h1>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-6">
            {error}
          </div>
        )}

        {loading ? (
          <div className="flex justify-center items-center h-64">
            <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-600"></div>
          </div>
        ) : submitting ? (
          <div className="flex justify-center items-center h-64">
            <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-600"></div>
            <span className="ml-4 text-gray-600">Updating product...</span>
          </div>
        ) : product ? (
          <div className="bg-white rounded-lg shadow-md p-6">
            <ProductForm 
              initialData={{
                sku: product.sku,
                name: product.name,
                description: product.description,
                price: product.price.toString(),
                stock: product.stock.toString(),
                imageUrl: product.imageUrl || '',
                category: product.category || '',
              }}
              onSubmit={handleSubmit}
              isEditing={true}
            />
          </div>
        ) : (
          <div className="text-center py-12">
            <p className="text-xl text-gray-600">Product not found.</p>
            <Link 
              to="/products" 
              className="inline-block mt-4 bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md transition duration-300"
            >
              Back to Products
            </Link>
          </div>
        )}
      </main>

      <Footer />
    </div>
  );
};

export default ProductEdit;
