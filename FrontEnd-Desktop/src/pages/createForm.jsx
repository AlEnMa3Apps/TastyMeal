import React from 'react';
import { Button_home } from '../components/button_home';
import { Card } from '@mui/material';
import { Menu_style } from '../styles/home_style';
import '../css/login.css';

const Create = () => {
  return (
    <div className='card_login'>
      <Card sx={Menu_style}>
        <h1>Crear nuevo usuario</h1>
        <form>
          
       </form>
      </Card>
      <Button_home />
    </div>
  );
};

export default Create;
