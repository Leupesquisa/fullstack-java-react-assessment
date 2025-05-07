import React from 'react';
import Button from '../atoms/Button';

interface NavbarProps {
  logo?: string;
  onLogoClick?: () => void;
  onCartClick?: () => void;
  cartItemsCount?: number;
  isLoggedIn?: boolean;
  onLoginClick?: () => void;
  onLogoutClick?: () => void;
  username?: string;
}

const Navbar: React.FC<NavbarProps> = ({
  logo,
  onLogoClick,
  onCartClick,
  cartItemsCount = 0,
  isLoggedIn = false,
  onLoginClick,
  onLogoutClick,
  username,
}) => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        {logo && (
          <img 
            src={logo} 
            alt="Company Logo" 
            className="navbar-logo" 
            onClick={onLogoClick}
          />
        )}
        <h1 className="navbar-title">E-Commerce Store</h1>
      </div>
      <div className="navbar-menu">
        <ul className="navbar-links">
          <li><a href="/">Home</a></li>
          <li><a href="/products">Products</a></li>
          <li><a href="/about">About</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </div>
      <div className="navbar-actions">
        <div className="cart-icon" onClick={onCartClick}>
          <span className="material-icons">shopping_cart</span>
          {cartItemsCount > 0 && (
            <span className="cart-count">{cartItemsCount}</span>
          )}
        </div>
        {isLoggedIn ? (
          <div className="user-menu">
            <span className="username">Hello, {username}</span>
            <Button 
              label="Logout" 
              onClick={onLogoutClick} 
              variant="outline"
              size="small"
            />
          </div>
        ) : (
          <Button 
            label="Login" 
            onClick={onLoginClick} 
            variant="primary"
            size="small"
          />
        )}
      </div>
    </nav>
  );
};

export default Navbar;