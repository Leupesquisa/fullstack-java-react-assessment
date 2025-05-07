import React from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/organisms/Navbar';
import Footer from '../components/organisms/Footer';
import { useAuth } from '../context/AuthContext';

const Home: React.FC = () => {
  const { isAuthenticated, user } = useAuth();

  return (
    <div className="flex flex-col min-h-screen">
      <Navbar 
        isLoggedIn={isAuthenticated}
        username={user?.email}
      />
      
      <main className="flex-grow">
        {/* Hero Section */}
        <section className="bg-gray-100 py-16 md:py-24">
          <div className="container mx-auto px-4 text-center">
            <h1 className="text-4xl md:text-5xl font-bold mb-4 text-gray-800">
              Welcome to E-Shop
            </h1>
            <p className="text-xl md:text-2xl mb-8 text-gray-600 max-w-3xl mx-auto">
              Discover the best products with amazing quality and affordable prices.
            </p>
            <Link 
              to="/products" 
              className="inline-block bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-8 rounded-md transition duration-300 transform hover:scale-105"
            >
              Shop Now
            </Link>
          </div>
        </section>
        
        {/* Features Section */}
        <section className="py-16">
          <div className="container mx-auto px-4">
            <h2 className="text-3xl font-bold mb-12 text-center text-gray-800">
              Why Choose Us
            </h2>
            
            <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
              <div className="bg-white p-6 rounded-lg shadow-md text-center">
                <div className="text-blue-600 text-4xl mb-4">
                  <span className="material-icons">local_shipping</span>
                </div>
                <h3 className="text-xl font-semibold mb-2">Fast Delivery</h3>
                <p className="text-gray-600">
                  Get your products delivered to your doorstep within 2-3 business days.
                </p>
              </div>
              
              <div className="bg-white p-6 rounded-lg shadow-md text-center">
                <div className="text-blue-600 text-4xl mb-4">
                  <span className="material-icons">verified</span>
                </div>
                <h3 className="text-xl font-semibold mb-2">Quality Products</h3>
                <p className="text-gray-600">
                  All our products are carefully selected to ensure the highest quality.
                </p>
              </div>
              
              <div className="bg-white p-6 rounded-lg shadow-md text-center">
                <div className="text-blue-600 text-4xl mb-4">
                  <span className="material-icons">support_agent</span>
                </div>
                <h3 className="text-xl font-semibold mb-2">24/7 Support</h3>
                <p className="text-gray-600">
                  Our customer support team is available round the clock to assist you.
                </p>
              </div>
            </div>
          </div>
        </section>
        
        {/* CTA Section */}
        <section className="bg-blue-600 py-16 text-white">
          <div className="container mx-auto px-4 text-center">
            <h2 className="text-3xl font-bold mb-4">
              Ready to start shopping?
            </h2>
            <p className="text-xl mb-8 max-w-2xl mx-auto">
              Join thousands of satisfied customers and experience the best online shopping.
            </p>
            <Link 
              to={isAuthenticated ? "/products" : "/login"} 
              className="inline-block bg-white text-blue-600 font-semibold py-3 px-8 rounded-md transition duration-300 hover:bg-gray-100"
            >
              {isAuthenticated ? "Browse Products" : "Sign Up Now"}
            </Link>
          </div>
        </section>
      </main>
      
      <Footer />
    </div>
  );
};

export default Home;