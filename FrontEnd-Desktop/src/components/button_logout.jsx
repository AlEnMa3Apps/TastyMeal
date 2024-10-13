import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import Link from '@mui/material/Link';



export function Button_logout () {

const navigate = useNavigate();

const handleLogoutClick = () => {
  //eliminar el token
  navigate('/'); 
};


    return(
    <Link component="button" variant="body2" onClick={handleLogoutClick}>
        Logout
    </Link>

)}


