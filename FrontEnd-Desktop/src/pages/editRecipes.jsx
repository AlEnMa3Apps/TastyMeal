import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchRecipeById, updateRecipe, deleteRecipe } from '../services/recipeService';

import '../css/editRecipe.css';

/**
 * EditRecipe component allows editing and deleting a specific recipe.
 *
 * This component fetches the recipe details based on the `recipeId` from the URL,
 * allows the user to update the details, or delete the recipe entirely.
 *
 * @component
 * @returns {JSX.Element} 
 * @example
 * // Example usage:
 * <EditRecipe />
 *
 * @author Enric Nanot Melchor
 */
const EditRecipe = () => {
    const { recipeId } = useParams();
    const navigate = useNavigate();

    /**
     * State to store the recipe details.
     * @typedef {Object} Recipe
     * @property {string} title - The title of the recipe.
     * @property {string} description - The description of the recipe.
     * @property {string} imageUrl - The image URL of the recipe.
     * @property {number} cookingTime - The cooking time in minutes.
     * @property {number} numPersons - The number of persons the recipe serves.
     * @property {string} ingredients - The ingredients for the recipe.
     * @property {Object} recipeCategory - The category of the recipe.
     * @property {string} recipeCategory.id - The ID of the category.
     * @property {string} recipeCategory.category - The name of the category.
     */
    const [recipe, setRecipe] = useState({
        title: '',
        description: '',
        imageUrl: '',
        cookingTime: 0,
        numPersons: 1,
        ingredients: '',
        recipeCategory: { id: '', category: '' },
    });

    /**
     * Fetches the recipe details when the component mounts or when `recipeId` changes.
     * Updates the state with the fetched recipe data.
     *
     * @function useEffect
     */
    useEffect(() => {
        const getRecipe = async () => {
            try {
                const data = await fetchRecipeById(recipeId);
                setRecipe(data);
            } catch (error) {
                console.error('Error al obtener la receta:', error);
            }
        };
        getRecipe();
    }, [recipeId]);

    /**
     * Handles changes in the form inputs and updates the recipe state.
     *
     * @function handleChange
     * @param {Object} e 
     * @param {string} e.target.name 
     * @param {string} e.target.value 
     */
    const handleChange = (e) => {
        const { name, value } = e.target;
        setRecipe((prevRecipe) => ({
            ...prevRecipe,
            [name]: value,
        }));
    };

    /**
     * Submits the updated recipe data to the backend.
     * Displays a success or error message based on the result.
     *
     * @function handleSubmit
     * @param {Object} e
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateRecipe(recipeId, recipe);
            alert('Receta actualizada con éxito.');
            navigate('/gestor');
        } catch (error) {
            console.error('Error al actualizar la receta:', error);
            alert('Hubo un problema al actualizar la receta.');
        }
    };

    /**
     * Deletes the recipe after user confirmation.
     * Navigates back to the recipe manager page upon success.
     *
     * @function handleDeleteRecipe
     * @param {number} id 
     */
    const handleDeleteRecipe = async (id) => {
        if (window.confirm('¿Estás seguro de que deseas eliminar esta receta?')) {
            try {
                await deleteRecipe(id);
                navigate('/gestor');
            } catch (error) {
                console.error('Error al eliminar la receta:', error);
            }
        }
    };

    const handleNavigateComments = () => {
        console.log("id receta es : " + recipeId);
        navigate(`/editComents/${recipeId}`)
    }

    return (
        <div className="form-container">
            <h1>Edit Recipe</h1>
            <form className="edit-form" onSubmit={handleSubmit}>
                <label htmlFor="title">Título:</label>
                <input
                    id="title"
                    type="text"
                    name="title"
                    value={recipe.title}
                    onChange={handleChange}
                />

                <label htmlFor="description">Descripción:</label>
                <textarea
                    id="description"
                    name="description"
                    value={recipe.description}
                    onChange={handleChange}
                />

                <label htmlFor="imageUrl">URL de Imagen:</label>
                <input
                    id="imageUrl"
                    type="text"
                    name="imageUrl"
                    value={recipe.imageUrl}
                    onChange={handleChange}
                />

                <label htmlFor="cookingTime">Tiempo de Cocción:</label>
                <input
                    id="cookingTime"
                    type="number"
                    name="cookingTime"
                    value={recipe.cookingTime}
                    onChange={handleChange}
                />

                <label htmlFor="numPersons">Número de Personas:</label>
                <input
                    id="numPersons"
                    type="number"
                    name="numPersons"
                    value={recipe.numPersons}
                    onChange={handleChange}
                />

                <label htmlFor="ingredients">Ingredientes:</label>
                <textarea
                    id="ingredients"
                    name="ingredients"
                    value={recipe.ingredients}
                    onChange={handleChange}
                />

                <label htmlFor="recipeCategory">Categoría:</label>
                <input
                    id="recipeCategory"
                    type="text"
                    name="recipeCategory"
                    value={recipe.recipeCategory?.category || ''}
                    onChange={(e) =>
                        setRecipe((prevRecipe) => ({
                            ...prevRecipe,
                            recipeCategory: {
                                ...prevRecipe.recipeCategory,
                                category: e.target.value,
                            },
                        }))
                    }
                />

                <div className="button-group">
                    <button type="submit" className="btn btn-save">
                        Guardar cambios
                    </button>
                    <button
                        type="button"
                        className="btn btn-cancel"
                        onClick={() => navigate('/gestor')}
                    >
                        Cancelar
                    </button>
                    <button
                        type="button"
                        className="btn btn-delete"
                        onClick={() => handleDeleteRecipe(recipeId)}
                    >
                        Borrar Receta
                    </button>
                </div>
                <div className='button-group'>
                    <button
                        type="button"
                        className="btn btn-cancel"
                        onClick={() => handleNavigateComments()}
                    >
                        Ver y editar comentarios
                    </button>
                    <button
                        type="button"
                        className="btn btn-cancel"
                        onClick={() => navigate(`/editReports/${recipeId}`)}
                    >
                        Ver y editar reportes
                    </button>
                </div>
            </form>
        </div>
    );
};

export default EditRecipe;
