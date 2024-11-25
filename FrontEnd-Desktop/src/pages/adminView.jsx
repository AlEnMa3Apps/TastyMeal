import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button_login } from '../components/button_login';
import { Card } from '@mui/material';
import { Admin_style } from '../styles/adminView';
import { Button_home } from '../components/button_home';
import { UserTable } from '../components/UsersTable'
/**
 * AdminView component represents the admin view page of the application.
 * It displays a title and includes a button to navigate back to the home page.
 *
 * @component
 * @returns {JSX.Element}
 * 
 * @example
 * // Example of rendering AdminView
 * <AdminView />
 * 
 * @author Enric Nanot Melchor
 */
const AdminView = () => {

  return (
    <div>
      <Card sx={Admin_style} >
        <h1>Admin View</h1>
        <UserTable />
        <Button_home />
      </Card>
    </div>
  );
};

export default AdminView;
