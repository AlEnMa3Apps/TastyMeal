import React from 'react';
import { HashRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import Home from './pages/home.jsx';
import Login from './pages/login.jsx';
import GestorView from './pages/gestorView.jsx';
import AdminView from './pages/adminView.jsx';
import ContactView from './pages/contact.jsx';
import AboutView from './pages/about.jsx';
import EditUser from './pages/editUser.jsx'
import EditRecipe from './pages/editRecipes.jsx';
import EditComments from './pages/editComments.jsx';
import EditReports from './pages/editReports.jsx';
import FavoriteRecipesUser from './pages/favoriteRecipes.jsx';

import { Header } from './components/header.jsx';
import Footer from './components/footer.jsx'

/**
 * Main application component that handles routing and layout.
 * Uses HashRouter for client-side routing and defines routes for different pages.
 * Includes a Header and Footer on all pages.
 *
 * @component
 * @returns {JSX.Element}
 * @author Enric Nanot Melchor
 * 
 * @example
 * // Example of the routes defined within the application
 * <App />
 */
const App = () => {
  return (
    <>
      <Router>
        <Header />
        <Routes>

          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/admin" element={<AdminView />} />
          <Route path="/gestor" element={<GestorView />} />
          <Route path="/contact" element={<ContactView />} />
          <Route path="/about" element={<AboutView />} />
          <Route path="/editUser/:userId" element={<EditUser />} />
          <Route path="/editRecipe/:recipeId" element={<EditRecipe />} />
          <Route path="/editComents/:recipeId" element={<EditComments />} />
          <Route path="/editReports/:recipeId" element={<EditReports />} />
          <Route path="/editUser/:userId/favorites" element={<FavoriteRecipesUser />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
        <Footer />
      </Router>
    </>
  );
};

export default App;
