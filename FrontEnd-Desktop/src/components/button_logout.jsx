import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import Link from '@mui/material/Link';

/**
 * Button component that logs the user out and navigates to the home page.
 *
 * @component
 * @returns {JSX.Element} A link component that acts as a logout button.
 *
 * @author Enric Nanot Melchor
 *
 * @example
 * // Example usage:
 * <Button_logout />
 */
export function Button_logout() {

  const navigate = useNavigate();

  /**
   * Function to handle the logout process. It removes the user's token
   * and redirects the user to the home page.
   */
  const handleLogoutClick = () => {

    navigate('/');
  };

  return (
    <Link component="button" variant="body2" onClick={handleLogoutClick}>
      Logout
    </Link>
  );
}
