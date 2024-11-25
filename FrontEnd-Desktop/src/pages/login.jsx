import React, { useState } from 'react';
import { Button_home } from '../components/button_home';

import { Button_iniciar } from '../components/button_iniciar';
import { Card } from '@mui/material';
import { Menu_style } from '../styles/home_style';
import Alert from '@mui/material/Alert';
import '../css/login.css';
import { useNavigate } from 'react-router-dom';
import { fetchlogin } from '../services/loginServices';

/**
 * Login component represents the login screen of the Tasty Meal application.
 * It allows users to input their username and password and initiates the login process.
 * Based on the user role, it navigates to different views (admin or gestor).
 *
 * @component
 * @returns {JSX.Element} 
 * 
 * @example
 * // Example of rendering the Login component
 * <Login />
 * 
 * @author Enric Nanot Melchor
 */
const Login = () => {
  const [showAlert, setShowAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  /**
   * Handles the login action by submitting the login form.
   * It sends the user's credentials to the login service and redirects the user based on their role.
   *
   * @param {React.FormEvent} event
   */
  const loginAction = async (event) => {
    event.preventDefault();

    const loginData = {
      username,
      password
    };

    const response = await fetchlogin(loginData);


    if (response.role === "ADMIN") {
      navigate('/admin');
    }
    if (response.role === "GESTOR") {
      navigate('/gestor');
    }
  };

  return (
    <div className='card_login'>
      <Card sx={Menu_style}>
        <h1>Login Screen</h1>


        {showAlert && (
          <Alert severity="success" sx={{ mb: 2 }}>
            {alertMessage}
          </Alert>
        )}

        <form onSubmit={loginAction}>
          <div className='form_login'>
            <label>Username:</label>
            <input
              type="text"
              name="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />

            <label>Password:</label>
            <input
              type="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <div style={{ display: 'flex', justifyContent: 'space-between' }}>
            <Button_iniciar type="submit" />

          </div>
        </form>
      </Card>

      <Button_home />
    </div>
  );
};

export default Login;
