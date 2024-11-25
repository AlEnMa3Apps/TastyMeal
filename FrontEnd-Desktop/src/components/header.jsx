import React from "react";
import { Link, NavLink } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import '../css/header.css';
import HomeIcon from '@mui/icons-material/Home';
import { IconButton } from "@mui/material";
import { Button_home } from "./button_home";
import { Button_logout } from "./button_logout";

/**
 * Header component for the Tasty Meal application. This component includes
 * a home button, a title, and a logout button. It conditionally renders the
 * logout button based on whether the user is authenticated.
 *
 * @component
 * @returns {JSX.Element} 
 *
 * @author Enric Nanot Melchor
 *
 * @example
 * // Example usage:
 * <Header />
 */
export function Header() {
  // const { userIsAuthenticated } = useAuth();  // will be used in the next implementation
  const userIsAuthenticated = false;

  const navigate = useNavigate();

  /**
   * Navigates to the home page when the user clicks on the home button.
   */
  const handleHomeClick = () => {
    navigate('/');
  };

  return (
    <header className="header">
      <div className="header_menu">
        <IconButton color="primary" onClick={handleHomeClick}>
          <HomeIcon />
        </IconButton>
        <h1>Tasty Meal</h1>
        {userIsAuthenticated ? <Button_logout /> : <Button_logout />}
      </div>
    </header>
  );
};
