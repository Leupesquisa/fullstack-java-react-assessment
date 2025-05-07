import React from 'react';

interface FooterProps {
  companyName?: string;
  year?: number;
  socialLinks?: {
    facebook?: string;
    twitter?: string;
    instagram?: string;
    linkedin?: string;
  };
}

const Footer: React.FC<FooterProps> = ({
  companyName = 'E-Commerce Store',
  year = new Date().getFullYear(),
  socialLinks = {},
}) => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <div className="footer-section">
          <h3>About Us</h3>
          <p>
            We are dedicated to providing the best shopping experience with
            high-quality products and excellent customer service.
          </p>
        </div>
        
        <div className="footer-section">
          <h3>Quick Links</h3>
          <ul className="footer-links">
            <li><a href="/">Home</a></li>
            <li><a href="/products">Products</a></li>
            <li><a href="/about">About</a></li>
            <li><a href="/contact">Contact</a></li>
            <li><a href="/privacy-policy">Privacy Policy</a></li>
            <li><a href="/terms-of-service">Terms of Service</a></li>
          </ul>
        </div>
        
        <div className="footer-section">
          <h3>Contact Us</h3>
          <p>Email: info@example.com</p>
          <p>Phone: +1 (123) 456-7890</p>
          <p>Address: 123 E-Commerce St, Shopping City, SC 12345</p>
        </div>
        
        <div className="footer-section">
          <h3>Follow Us</h3>
          <div className="social-links">
            {socialLinks.facebook && (
              <a href={socialLinks.facebook} target="_blank" rel="noopener noreferrer">
                <span className="social-icon">Facebook</span>
              </a>
            )}
            {socialLinks.twitter && (
              <a href={socialLinks.twitter} target="_blank" rel="noopener noreferrer">
                <span className="social-icon">Twitter</span>
              </a>
            )}
            {socialLinks.instagram && (
              <a href={socialLinks.instagram} target="_blank" rel="noopener noreferrer">
                <span className="social-icon">Instagram</span>
              </a>
            )}
            {socialLinks.linkedin && (
              <a href={socialLinks.linkedin} target="_blank" rel="noopener noreferrer">
                <span className="social-icon">LinkedIn</span>
              </a>
            )}
          </div>
        </div>
      </div>
      
      <div className="footer-bottom">
        <p>&copy; {year} {companyName}. All rights reserved.</p>
      </div>
    </footer>
  );
};

export default Footer;