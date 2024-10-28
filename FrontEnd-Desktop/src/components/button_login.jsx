import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';

/**
 * Button component that navigates the user to the login page when clicked.
 *
 * @component
 * @returns {JSX.Element} A button that redirects the user to the login page.
 *
 * @author Enric Nanot Melchor
 *
 * @example
 * // Example usage:
 * <Button_login />
 */
export function Button_login() {

  const navigate = useNavigate();

  /**
   * Function to handle the button click, navigating the user to the login page.
   */
  const handleLoginClick = () => {
    navigate('/login');
  };

  return (
    <Button variant="outlined" onClick={handleLoginClick}>
      Iniciar sesión
    </Button>
  );
}


