import React, { useState } from 'react';
import Input from '../atoms/Input';
import Button from '../atoms/Button';
import { ProductFormData } from '../../types';

interface ProductFormProps {
  initialData?: {
    sku: string;
    name: string;
    price: string;
    description: string;
    imageUrl: string;
    category: string;
    stock: string;
  };
  onSubmit: (productData: ProductFormData) => void;
  isEditing?: boolean;
}

const ProductForm: React.FC<ProductFormProps> = ({
  initialData = {
    sku: '',
    name: '',
    price: '',
    description: '',
    imageUrl: '',
    category: '',
    stock: '',
  },
  onSubmit,
  isEditing = false,
}) => {
  const [formData, setFormData] = useState(initialData);
  const [errors, setErrors] = useState<Record<string, string>>({});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const validate = () => {
    const newErrors: Record<string, string> = {};

    if (!formData.sku.trim()) {
      newErrors.sku = 'SKU is required';
    } else if (formData.sku.length < 3 || formData.sku.length > 50) {
      newErrors.sku = 'SKU must be between 3 and 50 characters';
    }

    if (!formData.name.trim()) {
      newErrors.name = 'Product name is required';
    } else if (formData.name.length < 3 || formData.name.length > 255) {
      newErrors.name = 'Product name must be between 3 and 255 characters';
    }

    if (!formData.price.trim()) {
      newErrors.price = 'Price is required';
    } else if (isNaN(parseFloat(formData.price)) || parseFloat(formData.price) <= 0) {
      newErrors.price = 'Price must be a positive number';
    }

    if (!formData.stock.trim()) {
      newErrors.stock = 'Stock quantity is required';
    } else if (isNaN(parseInt(formData.stock)) || parseInt(formData.stock) < 0) {
      newErrors.stock = 'Stock must be a non-negative integer';
    }

    if (!formData.category.trim()) {
      newErrors.category = 'Category is required';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (validate()) {
      onSubmit({
        ...formData,
        price: parseFloat(formData.price),
        stock: parseInt(formData.stock),
      });
    }
  };

  return (
    <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4" onSubmit={handleSubmit}>
      <h2 className="text-2xl font-bold text-gray-800 mb-6">{isEditing ? 'Edit Product' : 'Add New Product'}</h2>

      <div className="mb-6">
        <Input
          label="SKU"
          name="sku"
          value={formData.sku}
          onChange={handleChange}
          required
          disabled={isEditing} // SKU shouldn't be editable for existing products
          error={errors.sku}
          fullWidth
        />
      </div>

      <div className="mb-6">
        <Input
          label="Product Name"
          name="name"
          value={formData.name}
          onChange={handleChange}
          required
          error={errors.name}
          fullWidth
        />
      </div>

      <div className="mb-6">
        <Input
          label="Price"
          name="price"
          type="number"
          value={formData.price}
          onChange={handleChange}
          required
          error={errors.price}
          fullWidth
        />
      </div>

      <div className="mb-6">
        <label htmlFor="description" className="block text-gray-700 text-sm font-bold mb-2">
          Description
        </label>
        <textarea
          id="description"
          name="description"
          value={formData.description}
          onChange={handleChange}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline transition duration-300"
          rows={4}
        />
      </div>

      <div className="mb-6">
        <Input
          label="Image URL"
          name="imageUrl"
          value={formData.imageUrl}
          onChange={handleChange}
          fullWidth
        />
      </div>

      <div className="mb-6">
        <Input
          label="Category"
          name="category"
          value={formData.category}
          onChange={handleChange}
          required
          error={errors.category}
          fullWidth
        />
      </div>

      <div className="mb-6">
        <Input
          label="Stock Quantity"
          name="stock"
          type="number"
          value={formData.stock}
          onChange={handleChange}
          required
          error={errors.stock}
          fullWidth
        />
      </div>

      <div className="flex items-center justify-end">
        <Button
          label={isEditing ? 'Update Product' : 'Create Product'}
          variant="primary"
          size="medium"
          type="submit"
        />
      </div>
    </form>
  );
};

export default ProductForm;
