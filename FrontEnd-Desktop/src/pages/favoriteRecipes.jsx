import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getFavoriteRecipesByID } from '../services/favoriteRecipeService';
import { fetchRecipeById } from '../services/recipeService'; // Función que obtiene una receta por ID
import '../css/favoriteRecipes.css';

/**
 * FavoriteRecipesUser component displays the list of favorite recipes for a specific user.
 *
 * @component
 * @returns {JSX.Element}
 */
const FavoriteRecipesUser = () => {
    const { userId } = useParams();
    const [favoriteRecipes, setFavoriteRecipes] = useState([]); // Lista con los datos completos de recetas
    const navigate = useNavigate();

    useEffect(() => {
        const loadFavorites = async () => {
            try {
                // Obtiene las IDs de las recetas favoritas
                const recipeIds = await getFavoriteRecipesByID(userId);
                console.log("IDs de recetas favoritas:", recipeIds);

                // Realiza llamadas adicionales para obtener los nombres de las recetas
                const recipeDetailsPromises = recipeIds.map((id) => fetchRecipeById(id));
                const recipes = await Promise.all(recipeDetailsPromises);

                console.log("Detalles de las recetas favoritas:", recipes);
                setFavoriteRecipes(recipes);
            } catch (error) {
                console.error("Error al cargar las recetas favoritas:", error.response?.data || error.message);
            }
        };

        loadFavorites();
    }, [userId]);

    return (
        <div className="favorites-container">
            <h1>Recetas Favoritas del Usuario</h1>
            {favoriteRecipes.length > 0 ? (
                <ul className="favorites-list">
                    {favoriteRecipes.map((recipe) => (
                        <li key={recipe.id} className="favorite-item">
                            <h3>{recipe.title}</h3>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>Este usuario no tiene recetas favoritas aún.</p>
            )}
            <button className="btn btn-back" onClick={() => navigate(`/editUser/${userId}`)}>
                Volver
            </button>
        </div>
    );
};

export default FavoriteRecipesUser;
