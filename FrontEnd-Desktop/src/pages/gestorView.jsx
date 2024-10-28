import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button_login } from '../components/button_login';
import { Card } from '@mui/material';
import { Menu_style } from '../styles/home_style';
import { Button_home } from '../components/button_home';

/**
 * GestorView component represents the "manager's view" page of the application.
 * It displays a title and includes a button to navigate back to the home page.
 *
 * @component
 * @returns {JSX.Element} The rendered GestorView component.
 * 
 * @example
 * // Example of rendering GestorView
 * <GestorView />
 * 
 * @author Enric Nanot Melchor
 */
const GestorView = () => {

  return (
    <div>
      <Card sx={Menu_style}>
        <h1>Vista de gestor</h1>

        <Button_home />
      </Card>
    </div>
  );
};

export default GestorView;
