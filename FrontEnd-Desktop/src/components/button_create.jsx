import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';



export function Button_create () {

const navigate = useNavigate();

const handleCreateClick = () => {
  
  navigate('/create'); 
};


    return(

    <Button onClick = {handleCreateClick}>Crear cuenta</Button>

)}

