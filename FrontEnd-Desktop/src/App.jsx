import React from 'react';
import { HashRouter as Router, Routes, Route, Navigate } from 'react-router-dom'; // Usamos HashRouter

import Home from './pages/home.jsx';
import Login from './pages/login.jsx';
import Create from './pages/createForm.jsx';
import GestorView from './pages/gestorView.jsx';
import AdminView from './pages/adminView.jsx';
import ContactView from './pages/contact.jsx';
import AboutView from './pages/about.jsx';

import {Header} from './components/header.jsx';
import Footer from './components/footer.jsx'


const App = () => {  
  return ( 
    <>
      
      <Router>
        <Header/>
        <Routes>
          {/* Redirigir cualquier ruta desconocida a "/" */}
          <Route path="/" element={<Home />} />

          <Route path="/login" element={<Login />} />
          <Route path="/create" element={<Create />} />
          <Route path="/admin" element={<AdminView />} />
          <Route path="/gestor" element={<GestorView/>} />
          <Route path="/contact" element={<ContactView/>} />
          <Route path="/about" element={<AboutView/>} />

          {/* Opción para manejar rutas no encontradas */}
          <Route path="*" element={<Navigate to="/" />} />  
        </Routes>
        <Footer/>
      </Router>
     
    </>
      
    
  );
};

export default App;

