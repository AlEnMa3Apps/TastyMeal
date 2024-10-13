
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button_login } from '../components/button_login';
import { Card } from '@mui/material';
import { Menu_style } from '../styles/home_style';
const Home = () => {
 

  return (
    <div>
      <Card sx = {Menu_style}>
      <h1>Tasty Meal</h1>
      <Button_login/>
      </Card>
    </div>
  );
};

export default Home;
