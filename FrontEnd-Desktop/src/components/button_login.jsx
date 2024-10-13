import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';



export function Button_login () {

const navigate = useNavigate();

const handleLoginClick = () => {
  
  navigate('/login');  
};


    return(

    <Button variant="outlined" onClick = {handleLoginClick}>iniciar sesion</Button>

)}



