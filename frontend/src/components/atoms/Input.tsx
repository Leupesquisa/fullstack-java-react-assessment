import React from 'react';

interface InputProps {
  type?: 'text' | 'password' | 'email' | 'number';
  placeholder?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  name?: string;
  label?: string;
  required?: boolean;
  disabled?: boolean;
  error?: string;
  fullWidth?: boolean;
  id?: string;
  autoComplete?: string;
}

const Input: React.FC<InputProps> = ({
  type = 'text',
  placeholder,
  value,
  onChange,
  name,
  label,
  required = false,
  disabled = false,
  error,
  fullWidth = false,
}) => {
  // Base container classes
  const containerClasses = `mb-4 ${fullWidth ? 'w-full' : ''}`;

  // Label classes
  const labelClasses = 'block text-gray-700 text-sm font-bold mb-2';

  // Input classes
  const baseInputClasses = 'shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline transition duration-300';
  const errorClasses = error ? 'border-red-500' : 'border-gray-300 focus:border-blue-500';
  const disabledClasses = disabled ? 'bg-gray-100 cursor-not-allowed opacity-50' : '';

  return (
    <div className={containerClasses}>
      {label && (
        <label htmlFor={name} className={labelClasses}>
          {label} {required && <span className="text-red-500">*</span>}
        </label>
      )}
      <input
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        name={name}
        id={name}
        required={required}
        disabled={disabled}
        className={`${baseInputClasses} ${errorClasses} ${disabledClasses}`}
        aria-invalid={!!error}
        aria-describedby={error ? `${name}-error` : undefined}
      />
      {error && (
        <p className="text-red-500 text-xs italic mt-1" id={`${name}-error`}>
          {error}
        </p>
      )}
    </div>
  );
};

export default Input;
