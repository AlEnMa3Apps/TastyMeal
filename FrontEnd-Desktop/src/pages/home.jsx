import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button_login } from '../components/button_login';
import { Card } from '@mui/material';
import { Menu_style } from '../styles/home_style';

/**
 * Home component represents the main landing page of the Tasty Meal application.
 * It displays the application title and includes a login button for user authentication.
 *
 * @component
 * @returns {JSX.Element} 
 * 
 * @example
 * // Example of rendering Home
 * <Home />
 * 
 * @author Enric Nanot Melchor
 */
const Home = () => {

  return (
    <div>
      <Card sx={Menu_style}>
        <h1 color='black'>Tasty Meal</h1>
        <p color='black' >Desktop version</p>
        <Button_login />
      </Card>
    </div>
  );
};

export default Home;
