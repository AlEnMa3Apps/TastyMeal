import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

/**
 * Adds a comment to a recipe.
 *
 * @param {number} recipeId - The ID of the recipe.
 * @param {Object} commentData - The comment data to add.
 * @returns {Promise<Object>}
 * @throws Will throw an error if the request fails.
 */
export const addCommentToRecipe = async (recipeId, commentData) => {
    const token = localStorage.getItem('authToken');
    console.log('Cooment data:', commentData);
    const config = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    try {
        const response = await axios.post(`${API_URL}/recipe/${recipeId}/comment`, commentData, config);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Error al agregar el comentario22222:', error.response?.data || error.message);
        throw error;
    }
};

/**
 * Fetches all comments for a specific recipe.
 *
 * @param {number} recipeId - The ID of the recipe.
 * @returns {Promise<Array>}
 * @throws Will throw an error if the request fails.
 */
export const fetchCommentsForRecipe = async (recipeId) => {
    const token = localStorage.getItem('authToken');

    const config = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    try {
        const response = await axios.get(`${API_URL}/recipe/${recipeId}/comments`, config);
        return response.data;
    } catch (error) {
        console.error('Error al obtener los comentarios:', error.response?.data || error.message);
        throw error;
    }
};

/**
 * Edits a user's comment by its ID.
 *
 * @param {number} commentId - The ID of the comment.
 * @param {Object} commentData - The updated comment data.
 * @returns {Promise<Object>}
 * @throws Will throw an error if the request fails.
 */
export const editComment = async (commentId, commentData) => {
    const token = localStorage.getItem('authToken');

    const config = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    try {
        const response = await axios.put(`${API_URL}/comment/${commentId}`, commentData, config);
        return response.data;
    } catch (error) {
        console.error('Error al editar el comentario:', error.response?.data || error.message);
        throw error;
    }
};

/**
 * Deletes a comment by its ID
 *
 * @param {number} commentId - The ID of the comment.
 * @returns {Promise<Object>}
 * @throws Will throw an error if the request fails.
 */
export const deleteComment = async (commentId) => {
    const token = localStorage.getItem('authToken');

    const config = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    try {
        const response = await axios.delete(`${API_URL}/comment/a/${commentId}`, config);
        return response.data;
    } catch (error) {
        console.error('Error al eliminar el comentario ', error.response?.data || error.message);
        throw error;
    }
};
