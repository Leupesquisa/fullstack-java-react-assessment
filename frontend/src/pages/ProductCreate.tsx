import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/organisms/Navbar';
import Footer from '../components/organisms/Footer';
import ProductForm from '../components/organisms/ProductForm';
import { useAuth } from '../context/AuthContext';
import { productsAPI } from '../services/api';
import { ProductFormData, ApiError } from '../types';

const ProductCreate: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const { isAuthenticated, user } = useAuth();
  const navigate = useNavigate();

  // Check if user is authenticated
  useEffect(() => {
    if (!isAuthenticated) {
      setError('Please log in to create products');
      setTimeout(() => {
        navigate('/login', { state: { from: '/products/create' } });
      }, 2000);
    }
  }, [isAuthenticated, navigate]);

  const handleSubmit = async (productData: ProductFormData) => {
    try {
      setLoading(true);
      setError(null);

      const createdProduct = await productsAPI.createProduct(productData);
      navigate(`/products/${createdProduct.id}`, { state: { message: 'Product created successfully' } });
    } catch (err) {
      console.error('Error creating product:', err);

      const apiError = err as ApiError;

      if (apiError.response && apiError.response.status === 401) {
        setError('Please log in to create products');
        setTimeout(() => {
          navigate('/login', { state: { from: '/products/create' } });
        }, 2000);
      } else if (apiError.response && apiError.response.status === 400) {
        // Handle validation errors
        const errorMessage = apiError.response.data.message || 'Invalid product data';
        setError(`Validation error: ${errorMessage}`);
      } else {
        setError('Failed to create product. Please try again.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col min-h-screen">
      <Navbar 
        isLoggedIn={isAuthenticated}
        username={user?.email}
      />

      <main className="flex-grow container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-gray-800 mb-8">Create New Product</h1>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-6">
            {error}
          </div>
        )}

        {loading ? (
          <div className="flex justify-center items-center h-64">
            <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-600"></div>
          </div>
        ) : (
          <div className="bg-white rounded-lg shadow-md p-6">
            <ProductForm 
              onSubmit={handleSubmit}
              isEditing={false}
            />
          </div>
        )}
      </main>

      <Footer />
    </div>
  );
};

export default ProductCreate;
