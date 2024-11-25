import React, { useState, useEffect } from 'react';
import { fetchAllRecipes } from '../services/recipeService';
import { useNavigate } from 'react-router-dom';

/**
 * RecipeTable component renders a table displaying a list of recipes with their details
 * and provides an option to edit each recipe.
 *
 * This component fetches data from the backend using the `fetchAllRecipes` service 
 * and displays it in a tabular format. Users can click on the "Editar" button 
 * to navigate to the edit page for a specific recipe.
 *
 * @component
 * @returns {JSX.Element} 
 *
 * @example
 * // Example usage:
 * <RecipeTable />
 *
 * @author Enric Nanot Melchor
 */
export const RecipeTable = () => {
    const [recipes, setRecipes] = useState([]);
    const navigate = useNavigate();
    /**
     * Fetches the list of recipes from the backend and updates the state.
     */
    useEffect(() => {
        const getRecipes = async () => {
            try {
                const data = await fetchAllRecipes();
                setRecipes(data);
            } catch (error) {
                console.error('Error al obtener recetas:', error);
            }
        };
        getRecipes();
    }, []);

    /**
     * Handles the navigation to the edit page for a specific recipe.
     * 
     * @param {number} recipeId 
     */
    const handleEditRecipe = (recipeId) => {
        navigate(`/editRecipe/${recipeId}`);
    };

    return (
        <div>
            <h2>Recetas</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Tiempo de Cocción</th>
                        <th>Número de Personas</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {recipes.map((recipe) => (
                        <tr key={recipe.id}>
                            <td>{recipe.id}</td>
                            <td>{recipe.title}</td>
                            <td>{recipe.cookingTime} mins</td>
                            <td>{recipe.numPersons}</td>
                            <td>
                                <button onClick={() => handleEditRecipe(recipe.id)}>Editar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};
