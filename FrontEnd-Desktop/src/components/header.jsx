import React from "react";
import { Link, NavLink } from "react-router-dom";
import { useNavigate } from 'react-router-dom';

import '../css/header.css';
import HomeIcon from '@mui/icons-material/Home';
import { IconButton } from "@mui/material";

import { Button_home } from "./button_home";
import { Button_logout } from "./button_logout";

export function Header() {
//  const { userIsAuthenticated } = useAuth();
const userIsAuthenticated = false;
  
const navigate = useNavigate();

const handleHomeClick = () => {
  
  navigate('/'); 
};


  return (
    <header className="header">
      <div className="header_menu">   
      <IconButton color="primary" onClick={handleHomeClick}>
        <HomeIcon/>
        
      </IconButton> 
      <h1>Tasty Meal</h1>
      
      {userIsAuthenticated?<Button_logout/>:<Button_logout/>}
      </div>
  
    </header>
  )};

