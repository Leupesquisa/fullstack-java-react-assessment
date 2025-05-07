import React from 'react';
import Button from '../atoms/Button';

interface ProductCardProps {
  id: string;
  name: string;
  price: number;
  imageUrl: string;
  description?: string;
  onAddToCart?: () => void;
}

const ProductCard: React.FC<ProductCardProps> = ({
  id,
  name,
  price,
  imageUrl,
  description,
  onAddToCart,
}) => {
  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden transition-transform duration-300 hover:shadow-lg hover:scale-[1.02]">
      <div className="h-48 overflow-hidden">
        <img 
          src={imageUrl} 
          alt={name} 
          className="w-full h-full object-cover transition-transform duration-500 hover:scale-110"
        />
      </div>
      <div className="p-4">
        <h3 className="text-lg font-semibold text-gray-800 mb-2 truncate">{name}</h3>
        <p className="text-xl font-bold text-blue-600 mb-2">${price.toFixed(2)}</p>
        {description && (
          <p className="text-gray-600 text-sm mb-4 line-clamp-2">{description}</p>
        )}
        {onAddToCart && (
          <div className="mt-4">
            <Button 
              label="Add to Cart" 
              onClick={onAddToCart} 
              variant="primary"
              size="medium"
              fullWidth
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default ProductCard;
