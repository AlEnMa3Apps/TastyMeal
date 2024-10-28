import React from "react";
import Link from '@mui/material/Link';
import { useNavigate } from 'react-router-dom';
import '../css/footer.css';

/**
 * Footer component for the Tasty Meal application. It contains navigation links
 * to the "About Us" and "Contact" pages.
 *
 * @component
 * @returns {JSX.Element} The footer with navigation links and a heading.
 *
 * @author Enric Nanot Melchor
 *
 * @example
 * // Example usage:
 * <Footer />
 */
const Footer = () => {

  const navigate = useNavigate();

  /**
   * Navigates to the contact page when the user clicks on the "Contact" link.
   */
  const handleContactClick = () => {
    navigate('/contact');
  };

  /**
   * Navigates to the about page when the user clicks on the "About Us" link.
   */
  const handleAboutClick = () => {
    navigate('/about');
  };

  return (
    <header className="footer">
      <div className="footer_menu">
        <Link component="button" variant="body2" onClick={handleAboutClick}>
          About us
        </Link>
        <h1>Tasty Meal</h1>
        <Link component="button" variant="body2" onClick={handleContactClick}>
          Contact
        </Link>
      </div>
    </header>
  );
};

export default Footer;
