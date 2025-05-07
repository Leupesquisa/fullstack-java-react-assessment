// Define interfaces for API data

export interface User {
  id: string;
  email: string;
  firstName?: string;
  lastName?: string;
  role: string;
  createdAt?: string;
}

export interface Product {
  id: string;
  sku: string;
  name: string;
  description: string;
  price: number;
  stock: number;
  category?: string;
  imageUrl?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface LoginResponse {
  token: string;
  user: User;
}

// Additional types for form handling
export interface ProductFormData {
  sku: string;
  name: string;
  description: string;
  price: number | string;
  stock: number | string;
  category?: string;
  imageUrl?: string;
}

// API Error Response
export interface ApiErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
}

// Axios Error with response typing
export interface ApiError extends Error {
  response?: {
    data: ApiErrorResponse;
    status: number;
    headers: Record<string, string>;
  };
}
