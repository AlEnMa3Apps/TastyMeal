import axios from 'axios';

const API_URL = 'https://localhost:8443/api';

/**
 * Guarda una receta como favorita para el usuario actual.
 *
 * @param {number} recipeId - ID de la receta a marcar como favorita.
 * @returns {Promise<Object>} - Respuesta del servidor.
 * @throws {Error} - Error en caso de fallo en la petición.
 */
export const addFavoriteRecipe = async (recipeId) => {
    const token = localStorage.getItem('authToken'); // Obtén el token de autenticación almacenado
    try {
        const response = await axios.post(
            `${API_URL}/recipe/${recipeId}/favorite`,
            {},
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        return response.data;
    } catch (error) {
        console.error('Error al añadir la receta a favoritos:', error);
        throw error;
    }
};

/**
 * Elimina una receta de la lista de favoritas del usuario actual.
 *
 * @param {number} recipeId - ID de la receta a eliminar de favoritos.
 * @returns {Promise<Object>} - Respuesta del servidor.
 * @throws {Error} - Error en caso de fallo en la petición.
 */
export const removeFavoriteRecipe = async (recipeId) => {
    const token = localStorage.getItem('authToken'); // Obtén el token de autenticación
    try {
        const response = await axios.delete(
            `${API_URL}/recipe/${recipeId}/favorite`,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        return response.data;
    } catch (error) {
        console.error('Error al eliminar la receta de favoritos:', error);
        throw error;
    }
};

/**
 * Obtiene la lista de recetas favoritas del usuario actual.
 *
 * @returns {Promise<Array<number>>} - Lista de IDs de recetas favoritas.
 * @throws {Error} - Error en caso de fallo en la petición.
 */
export const getFavoriteRecipes = async () => {
    const token = localStorage.getItem('authToken');
    try {
        const response = await axios.get(`${API_URL}/recipes/favorite`, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        console.log("Respuesta del backend:", response.data);
        return response.data;
    } catch (error) {
        console.error('Error al obtener las recetas favoritas:', error.response?.data || error.message);
        alert("Error al cargar las recetas favoritas: " + (error.response?.data?.message || error.message));
        throw error;
    }
};

/**
 * Obtiene las recetas favoritas de un usuario específico por su ID.
 * 
 * @param {number} userId - El ID del usuario cuyas recetas favoritas se desean obtener.
 * @returns {Promise<Array>} - Lista de recetas favoritas del usuario.
 */
export const getFavoriteRecipesByID = async (userId) => {
    const token = localStorage.getItem('authToken');
    try {
        const response = await axios.get(`${API_URL}/user/${userId}/recipes/favorite`, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        console.log("Respuesta del backend:", response.data);
        return response.data;
    } catch (error) {
        console.error('Error al obtener las recetas favoritas:', error.response?.data || error.message);
        alert("Error al cargar las recetas favoritas: " + (error.response?.data?.message || error.message));
        throw error;
    }
};