import { ApiError } from '../types';

export const isApiError = (error: unknown): error is ApiError => {
  return (
    error !== null &&
    typeof error === 'object' &&
    'response' in error &&
    error.response !== null &&
    error.response !== undefined &&
    typeof error.response === 'object' &&
    'data' in error.response &&
    'status' in error.response
  );
};

export const getErrorMessage = (error: unknown): string => {
  if (isApiError(error) && error.response) {
    if (error.response.status === 401) {
      return 'Authentication failed. Please check your credentials.';
    }
    if (error.response.status === 409) {
      return 'Email already exists.';
    }
    return error.response.data.message || 'An unexpected error occurred';
  }
  
  if (error instanceof Error) {
    return error.message;
  }
  
  return 'An unexpected error occurred';
};