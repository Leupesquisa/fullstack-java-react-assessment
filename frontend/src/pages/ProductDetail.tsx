import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import Navbar from '../components/organisms/Navbar';
import Footer from '../components/organisms/Footer';
import { useAuth } from '../context/AuthContext';
import { productsAPI } from '../services/api';
import { Product, ApiError } from '../types';

const ProductDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const { isAuthenticated, user } = useAuth();
  const navigate = useNavigate();

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

        // Handle different error types
        if (apiError.response && apiError.response.status === 401) {
          setError('Please log in to view product details');
          setTimeout(() => {
            navigate('/login', { state: { from: `/products/${id}` } });
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

    fetchProduct();
  }, [id, navigate]);

  const handleDeleteProduct = async () => {
    if (!product) return;

    if (window.confirm('Are you sure you want to delete this product?')) {
      try {
        await productsAPI.deleteProduct(product.id);
        navigate('/products', { state: { message: 'Product deleted successfully' } });
      } catch (err) {
        console.error('Error deleting product:', err);

        const apiError = err as ApiError;

        if (apiError.response && apiError.response.status === 401) {
          setError('Please log in to delete products');
          setTimeout(() => {
            navigate('/login', { state: { from: `/products/${id}` } });
          }, 2000);
        } else if (apiError.response && apiError.response.status === 403) {
          setError('You do not have permission to delete this product');
        } else if (apiError.response && apiError.response.status === 404) {
          setError('Product not found');
        } else {
          setError('Failed to delete product. Please try again.');
        }
      }
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
            to="/products" 
            className="text-blue-600 hover:text-blue-800 flex items-center"
          >
            <span className="mr-2">←</span> Back to Products
          </Link>
        </div>

        {loading ? (
          <div className="flex justify-center items-center h-64">
            <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-600"></div>
          </div>
        ) : error ? (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-6">
            {error}
          </div>
        ) : product ? (
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <div className="md:flex">
              <div className="md:flex-shrink-0 md:w-1/2">
                <img 
                  className="h-full w-full object-cover md:h-full md:w-full" 
                  src={product.imageUrl || 'https://via.placeholder.com/600x400'} 
                  alt={product.name} 
                />
              </div>

              <div className="p-8 md:w-1/2">
                <div className="uppercase tracking-wide text-sm text-blue-600 font-semibold">
                  Product ID: {product.id}
                </div>

                <h1 className="mt-2 text-3xl font-bold text-gray-900">
                  {product.name}
                </h1>

                <div className="mt-4 flex items-center">
                  <span className="text-2xl font-bold text-gray-900">
                    ${product.price.toFixed(2)}
                  </span>

                  <span className="ml-4 px-2 py-1 bg-green-100 text-green-800 text-xs font-semibold rounded-full">
                    {product.stock > 0 ? `In Stock (${product.stock})` : 'Out of Stock'}
                  </span>
                </div>

                <div className="mt-6">
                  <h2 className="text-lg font-semibold text-gray-900">Description</h2>
                  <p className="mt-2 text-gray-600">
                    {product.description}
                  </p>
                </div>

                {isAuthenticated && (
                  <div className="mt-8 flex space-x-4">
                    <Link 
                      to={`/products/edit/${product.id}`}
                      className="flex-1 bg-blue-600 hover:bg-blue-700 text-white text-center font-semibold py-2 px-4 rounded-md transition duration-300"
                    >
                      Edit Product
                    </Link>

                    <button 
                      onClick={handleDeleteProduct}
                      className="flex-1 bg-red-600 hover:bg-red-700 text-white text-center font-semibold py-2 px-4 rounded-md transition duration-300"
                    >
                      Delete Product
                    </button>
                  </div>
                )}
              </div>
            </div>
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

export default ProductDetail;
