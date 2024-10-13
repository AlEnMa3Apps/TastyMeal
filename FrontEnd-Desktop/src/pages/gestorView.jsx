
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button_login } from '../components/button_login';
import { Card } from '@mui/material';
import { Menu_style } from '../styles/home_style';
import { Button_home } from '../components/button_home';


const GestorView = () => {
 

  return (
    <div>
      <Card sx = {Menu_style}>
      <h1>Vista de gestor</h1>
      <Button_home/>
      </Card>
    </div>
  );
};

export default GestorView;