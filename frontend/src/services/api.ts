import axios, { AxiosInstance, InternalAxiosRequestConfig } from 'axios';
import { User, Product, LoginResponse } from '../types';

// Create an Axios instance with the base URL and default headers
const api: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add a request interceptor to include the JWT token in the Authorization header
api.interceptors.request.use(
  (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Authentication API calls
export const authAPI = {
  register: async (email: string, password: string, role: string = 'USER'): Promise<User> => {
    const response = await api.post('/auth/register', { email, password, role });
    return response.data;
  },

  login: async (email: string, password: string): Promise<LoginResponse> => {
    const response = await api.post('/auth/login', { email, password });
    return response.data;
  },
};

// Products API calls
export const productsAPI = {
  getProducts: async (): Promise<Product[]> => {
    const response = await api.get('/products');
    // Handle paginated response from backend
    return response.data.content || response.data;
  },

  getProduct: async (id: string): Promise<Product> => {
    const response = await api.get(`/products/${id}`);
    return response.data;
  },

  createProduct: async (product: ProductFormData): Promise<Product> => {
    // Ensure numeric values for price and stock
    const formattedProduct = {
      ...product,
      price: typeof product.price === 'string' ? parseFloat(product.price) : product.price,
      stock: typeof product.stock === 'string' ? parseInt(product.stock.toString()) : product.stock
    };

    const response = await api.post('/products', formattedProduct);
    return response.data;
  },

  updateProduct: async (id: string, product: ProductFormData): Promise<Product> => {
    // Ensure numeric values for price and stock
    const formattedProduct = {
      ...product,
      price: typeof product.price === 'string' ? parseFloat(product.price) : product.price,
      stock: typeof product.stock === 'string' ? parseInt(product.stock.toString()) : product.stock
    };

    const response = await api.put(`/products/${id}`, formattedProduct);
    return response.data;
  },

  deleteProduct: async (id: string): Promise<void> => {
    await api.delete(`/products/${id}`);
  },
};

export default api;
