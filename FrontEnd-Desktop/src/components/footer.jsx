import React from "react";

import Link from '@mui/material/Link';
import { useNavigate } from 'react-router-dom';


import '../css/footer.css';

const Footer = () => {
//  const { userIsAuthenticated } = useAuth();

const navigate = useNavigate();

const handleContacClick = () => {
  
  navigate('/contact'); 
};

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
      <Link component="button" variant="body2" onClick={handleContacClick}>
            Contact
      </Link>
      </div>
  
    </header>
  );
};

export default Footer;