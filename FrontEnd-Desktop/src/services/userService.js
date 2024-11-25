import axios from 'axios';

/**
 * Fetches user details by user ID.
 *
 * @param {number} id
 * @returns {Promise<Object>} 
 * @throws Will throw an error if the request fails.
 */
export const fetchUserById = async (id) => {
    const token = localStorage.getItem('authToken');
    console.log("Token ", token);

    const response = await fetch(`http://localhost:8080/api/user/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });


    if (!response.ok) {
        throw new Error(`Error ${response.status}: ${response.statusText}`);
    }


    const data = await response.json();
    return data;
};

/**
 * Updates a user by their ID.
 *
 * @param {number} id 
 * @param {Object} userData 
 * @param {string} [authToken] 
 * @returns {Promise<Object>}
 * @throws Will throw an error if the request fails.
 */
export const updateUser = async (id, userData, authToken) => {
    const token = localStorage.getItem('authToken') || authToken;
    const config = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    try {
        const response = await axios.put(`http://localhost:8080/api/user/${id}`, userData, config);
        console.log('Datos enviados:', userData);
        console.log('Respuesta del servidor:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error al actualizar el usuario:', error.response ? error.response.data : error.message);
        throw error;
    }
};

/**
 * Deletes a user by their ID.
 *
 * @param {number} id 
 * @param {string} [authToken] 
 * @returns {Promise<Object>} 
 * @throws Will throw an error if the request fails.
 */
export const DeleteUser = async (id, authToken) => {
    const token = localStorage.getItem('authToken') || authToken;
    const config = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    try {
        const response = await axios.delete(`http://localhost:8080/api/user/${id}`, config);
        console.log('Respuesta del servidor:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error al eliminar el usuario:', error.response ? error.response.data : error.message);
        throw error;
    }
};
