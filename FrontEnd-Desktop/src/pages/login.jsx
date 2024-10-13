import React, { useState } from 'react';

import { Button_home } from '../components/button_home';
import { Button_create } from '../components/button_create';
import { Button_iniciar } from '../components/button_iniciar';
import { Button_adminView } from '../components/button_adminView';
import { Button_gestorView } from '../components/button_gestorView';

import { Card } from '@mui/material';
import { Menu_style } from '../styles/home_style';
import Alert from '@mui/material/Alert';

import '../css/login.css';

const Login = () => {
  const [showAlert, setShowAlert] = useState(false); // Estado para controlar la alerta

  function loginAction(event) {
    event.preventDefault(); // Evitar que el formulario se envíe y la página se recargue
    
    setShowAlert(true); // Mostrar la alerta al enviar el formulario
  }

  return (
    <div className='card_login'>
      <Card sx={Menu_style}>
        <h1>Pantalla de Inicio de Sesión</h1>

        {/* Mostrar alerta condicionalmente */}
        {showAlert && (
          <Alert severity="success" sx={{ mb: 2 }}>
            Inicio de sesión exitoso.
          </Alert>
        )}

        <form onSubmit={loginAction}>
          <div className='form_login'>
            <label>Usuario:</label>
            <input type="text" name="username" />
            
            <label>Contraseña:</label>
            <input type="password" name="password" />
          </div>
          <div style={{ display:'flex' ,justifyContent:'space-between' }}>
            <Button_iniciar type="submit" />
            <Button_create />
          </div>
        </form>
      </Card>

      <Button_home />
      <Button_adminView />
      <Button_gestorView />
    </div>
  );
};

export default Login;
