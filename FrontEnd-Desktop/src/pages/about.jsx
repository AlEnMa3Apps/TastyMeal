import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button_login } from '../components/button_login';
import { Card } from '@mui/material';
import { Menu_style } from '../styles/home_style';
import { Button_home } from '../components/button_home';

/**
 * AboutView component represents the "About" page of the application.
 * It includes a title and a button to navigate back to the home page.
 *
 * @component
 * @returns {JSX.Element} The rendered AboutView component.
 * 
 * @example
 * // Example of rendering AboutView
 * <AboutView />
 * 
 * @author Enric Nanot Melchor
 */
const AboutView = () => {

  return (
    <div>
      <Card sx={Menu_style}>
        <h1>About</h1>

        <Button_home />
      </Card>
    </div>
  );
};

export default AboutView;
