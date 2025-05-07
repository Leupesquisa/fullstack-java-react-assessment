import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Navbar from '../components/organisms/Navbar';
import Footer from '../components/organisms/Footer';
import ProductCard from '../components/molecules/ProductCard';
import { useAuth } from '../context/AuthContext';
import { productsAPI } from '../services/api';
import { Product, ApiError } from '../types';

const Products: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const { isAuthenticated, user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        const data = await productsAPI.getProducts();
        setProducts(data);
        setError(null);
      } catch (err) {
        console.error('Error fetching products:', err);

        const apiError = err as ApiError;

        // Handle different error types
        if (apiError.response && apiError.response.status === 401) {
          setError('Please log in to view products');
          setTimeout(() => {
            navigate('/login', { state: { from: '/products' } });
          }, 2000);
        } else if (apiError.response && apiError.response.status === 403) {
          setError('You do not have permission to view products');
        } else {
          setError('Failed to load products. Please try again later.');
        }
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [navigate]);

  const handleDeleteProduct = async (id: string) => {
    if (window.confirm('Are you sure you want to delete this product?')) {
      try {
        await productsAPI.deleteProduct(id);
        setProducts(products.filter(product => product.id !== id));
      } catch (err) {
        console.error('Error deleting product:', err);

        const apiError = err as ApiError;

        if (apiError.response && apiError.response.status === 401) {
          setError('Please log in to delete products');
          setTimeout(() => {
            navigate('/login', { state: { from: '/products' } });
          }, 2000);
        } else if (apiError.response && apiError.response.status === 403) {
          setError('You do not have permission to delete this product');
        } else if (apiError.response && apiError.response.status === 404) {
          setError('Product not found');
          // Remove the product from the local state if it's not found on the server
          setProducts(products.filter(product => product.id !== id));
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
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">Products</h1>

          {isAuthenticated && (
            <Link 
              to="/products/create" 
              className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md transition duration-300"
            >
              Create Product
            </Link>
          )}
        </div>

        {loading ? (
          <div className="flex justify-center items-center h-64">
            <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-600"></div>
          </div>
        ) : error ? (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-6">
            {error}
          </div>
        ) : products.length === 0 ? (
          <div className="text-center py-12">
            <p className="text-xl text-gray-600">No products found.</p>
            {isAuthenticated && (
              <Link 
                to="/products/create" 
                className="inline-block mt-4 bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md transition duration-300"
              >
                Add Your First Product
              </Link>
            )}
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {products.map((product) => (
              <div key={product.id} className="flex flex-col h-full">
                <ProductCard
                  id={product.id.toString()}
                  name={product.name}
                  price={product.price}
                  imageUrl={product.imageUrl || 'https://via.placeholder.com/300x200'}
                  description={product.description.length > 50 
                    ? `${product.description.substring(0, 50)}...` 
                    : product.description}
                />

                <div className="mt-auto pt-4 flex space-x-2">
                  <Link 
                    to={`/products/${product.id}`}
                    className="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-800 text-center py-2 px-4 rounded-md transition duration-300"
                  >
                    View Details
                  </Link>

                  {isAuthenticated && (
                    <>
                      <Link 
                        to={`/products/edit/${product.id}`}
                        className="flex-1 bg-blue-100 hover:bg-blue-200 text-blue-800 text-center py-2 px-4 rounded-md transition duration-300"
                      >
                        Edit
                      </Link>

                      <button 
                        onClick={() => handleDeleteProduct(product.id)}
                        className="flex-1 bg-red-100 hover:bg-red-200 text-red-800 text-center py-2 px-4 rounded-md transition duration-300"
                      >
                        Delete
                      </button>
                    </>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </main>

      <Footer />
    </div>
  );
};

export default Products;
