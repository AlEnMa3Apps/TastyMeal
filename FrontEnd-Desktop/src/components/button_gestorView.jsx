import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';



export function Button_gestorView () {

const navigate = useNavigate();

const handleGestorClick = () => {
  
  navigate('/gestor'); 
};


    return(

    <Button variant="outlined" onClick = {handleGestorClick}>Preview gestor view</Button>

)}
