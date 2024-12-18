import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
    fetchRecipeById,
    updateRecipe,
    deleteRecipe,
} from '../services/recipeService';
import {
    addFavoriteRecipe,
    removeFavoriteRecipe,
    getFavoriteRecipes,
} from '../services/favoriteRecipeService';

import '../css/editRecipe.css';

/**
 * EditRecipe component allows editing, deleting, and managing favorites for a specific recipe.
 *
 * @component
 * @returns {JSX.Element}
 */
const EditRecipe = () => {
    const { recipeId } = useParams();
    const navigate = useNavigate();

    const [recipe, setRecipe] = useState({
        title: '',
        description: '',
        imageUrl: '',
        cookingTime: 0,
        numPersons: 1,
        ingredients: '',
        recipeCategory: { id: '', category: '' },
    });

    const [isFavorite, setIsFavorite] = useState(false);

    useEffect(() => {
        const loadRecipe = async () => {
            try {
                const data = await fetchRecipeById(recipeId);
                setRecipe(data);


                const favorites = await getFavoriteRecipes();
                setIsFavorite(favorites.includes(Number(recipeId)));
            } catch (error) {
                console.error('Error al obtener la receta o favoritos:', error);
            }
        };
        loadRecipe();
    }, [recipeId]);

    const handleFavoriteToggle = async () => {
        try {
            if (isFavorite) {
                await removeFavoriteRecipe(recipeId);
            } else {
                await addFavoriteRecipe(recipeId);
            }
            setIsFavorite(!isFavorite);
        } catch (error) {
            console.error('Error al actualizar el favorito:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateRecipe(recipeId, recipe);
            alert('Receta actualizada con éxito.');
            navigate('/gestor');
        } catch (error) {
            console.error('Error al actualizar la receta:', error);
        }
    };

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
                    onChange={(e) =>
                        setRecipe({ ...recipe, [e.target.name]: e.target.value })
                    }
                />

                <label htmlFor="description">Descripción:</label>
                <textarea
                    id="description"
                    name="description"
                    value={recipe.description}
                    onChange={(e) =>
                        setRecipe({ ...recipe, [e.target.name]: e.target.value })
                    }
                />

                <label htmlFor="imageUrl">URL de Imagen:</label>
                <input
                    id="imageUrl"
                    type="text"
                    name="imageUrl"
                    value={recipe.imageUrl}
                    onChange={(e) =>
                        setRecipe({ ...recipe, [e.target.name]: e.target.value })
                    }
                />

                <label htmlFor="cookingTime">Tiempo de Cocción:</label>
                <input
                    id="cookingTime"
                    type="number"
                    name="cookingTime"
                    value={recipe.cookingTime}
                    onChange={(e) =>
                        setRecipe({ ...recipe, [e.target.name]: e.target.value })
                    }
                />

                <label htmlFor="numPersons">Número de Personas:</label>
                <input
                    id="numPersons"
                    type="number"
                    name="numPersons"
                    value={recipe.numPersons}
                    onChange={(e) =>
                        setRecipe({ ...recipe, [e.target.name]: e.target.value })
                    }
                />

                <label htmlFor="ingredients">Ingredientes:</label>
                <textarea
                    id="ingredients"
                    name="ingredients"
                    value={recipe.ingredients}
                    onChange={(e) =>
                        setRecipe({ ...recipe, [e.target.name]: e.target.value })
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

                <div className="favorite-container">
                    <button
                        type="button"
                        className={`btn-favorite ${isFavorite ? 'filled' : ''}`}
                        onClick={handleFavoriteToggle}
                    >
                        {isFavorite ? '❤️' : '🤍'}
                    </button>

                </div>
            </form>
        </div>
    );
};

export default EditRecipe;
