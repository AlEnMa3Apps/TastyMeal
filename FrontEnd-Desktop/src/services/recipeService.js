import axios from 'axios';


const API_URL = 'https://localhost:8443/api';

/**
 * Fetches all recipes from the backend.
 *
 * @returns {Promise<Array>}
 * @throws Will throw an error if the request fails.
 */
export const fetchAllRecipes = async () => {
    const token = localStorage.getItem('authToken');
    console.log(token);

    return axios.get(`${API_URL}/recipes/all`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error('Error al obtener todas las recetas:');
            if (error.response && error.response.data) {
                console.error('Error del servidor:', error.response.data);
            } else {
                console.error('Error desconocido.');
            }
            throw error;
        });
};

/**
 * Fetches a specific recipe by its ID.
 *
 * @param {number} id 
 * @returns {Promise<Object>} 
 * @throws Will throw an error if the request fails.
 */
export const fetchRecipeById = async (id) => {
    const token = localStorage.getItem('authToken');
    console.log(token);

    const config = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    try {
        const response = await axios.get(`${API_URL}/recipe/${id}`, config);
        return response.data;
    } catch (error) {
        console.error('Error al obtener la receta:', error.response?.data || error.message);
        throw error;
    }
};

/**
 * Updates a recipe by its ID.
 *
 * @param {number} id 
 * @param {Object} recipeData 
 * @returns {Promise<Object>}
 * @throws Will throw an error if the request fails.
 */
export const updateRecipe = async (id, recipeData) => {
    const token = localStorage.getItem('authToken');

    const config = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    try {
        const response = await axios.put(`${API_URL}/recipe/${id}`, recipeData, config);
        return response.data;
    } catch (error) {
        console.error('Error al actualizar la receta:', error.response?.data || error.message);
        throw error;
    }
};

/**
 * Deletes a recipe by its ID.
 *
 * @param {number} id 
 * @returns {Promise<Object>}
 * @throws Will throw an error if the request fails.
 */
export const deleteRecipe = async (id) => {
    const token = localStorage.getItem('authToken');

    const config = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    try {
        const response = await axios.delete(`${API_URL}/recipe/${id}`, config);
        return response.data;
    } catch (error) {
        console.error('Error al eliminar la receta:', error.response?.data || error.message);
        throw error;
    };
}
