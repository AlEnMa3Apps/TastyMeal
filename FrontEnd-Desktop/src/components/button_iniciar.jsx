import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';




export function Button_iniciar() {

  const navigate = useNavigate();



  const handleHomeClick = () => {

    navigate('/');
  };


  return (

    <Button onClick={handleHomeClick} type="submit" >Iniciar sesión</Button>

  )
}