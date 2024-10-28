import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';

/**
 * Button_home component renders a button that, when clicked, navigates the user back to the home page ('/').
 * 
 * @component
 * @example
 * return (
 *   <Button_home />
 * )
 * 
 * @returns {JSX.Element} The rendered button component.
 * 
 * @author Enric Nanot Melchor
 */
export function Button_home() {

  const navigate = useNavigate();

  /**
   * handleHomeClick is triggered when the button is clicked. It navigates the user to the home route ('/').
   */
  const handleHomeClick = () => {
    navigate('/');
  };

  return (
    <Button variant="outlined" onClick={handleHomeClick}>Back to home</Button>
  );
}
