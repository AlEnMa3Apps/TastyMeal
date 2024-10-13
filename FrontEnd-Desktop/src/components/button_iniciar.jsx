import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import { useToast } from "../components/externalComp/use-toast"



export function Button_iniciar () {

const navigate = useNavigate();

const { toast } = useToast()

const handleHomeClick = () => {
  toast({
    title: "Scheduled: Catch up",
    description: "Friday, February 10, 2023 at 5:57 PM",
  })
 // navigate('/'); 
};


    return(

    <Button onClick = {handleHomeClick} type="submit" >Iniciar sesión</Button>

)}