import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';



export function Button_home () {

const navigate = useNavigate();

const handleHomeClick = () => {
  
  navigate('/'); 
};


    return(

    <Button variant="outlined" onClick = {handleHomeClick}>Back to home</Button>

)}



