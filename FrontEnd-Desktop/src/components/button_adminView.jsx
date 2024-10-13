import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';



export function Button_adminView () {

const navigate = useNavigate();

const handleAdminClick = () => {
  
  navigate('/admin'); 
};


    return(

    <Button variant="outlined" onClick = {handleAdminClick}>Preview admin view</Button>

)}
