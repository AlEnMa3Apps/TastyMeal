import axios from 'axios';

const API_URL = 'https://localhost:8443/api'; // URL base de l'API

/**
 * Afegir un report a una recepta.
 *
 * @param {number} recipeId - ID de la recepta on afegir el report.
 * @param {Object} reportData - Contingut del report { report: "text del report" }.
 * @returns {Promise<Object>} Report creat.
 */
export const addReportToRecipe = async (recipeId, reportData) => {
    const token = localStorage.getItem('authToken');

    try {
        const response = await axios.post(`${API_URL}/recipe/${recipeId}/report`, reportData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error al afegir el report:', error.response?.data || error.message);
        throw error;
    }
};

/**
 * Obtenir tots els reports d'una recepta.
 *
 * @param {number} recipeId - ID de la recepta.
 * @returns {Promise<Array>} Llistat de reports de la recepta.
 */
export const fetchReportsForRecipe = async (recipeId) => {
    const token = localStorage.getItem('authToken');

    try {
        const response = await axios.get(`${API_URL}/recipe/${recipeId}/reports`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error al obtenir els reports:', error.response?.data || error.message);
        throw error;
    }
};

/**
 * Editar un report existent.
 *
 * @param {number} reportId - ID del report a editar.
 * @param {Object} reportData - Nou contingut del report { report: "text actualitzat" }.
 * @returns {Promise<Object>} Report actualitzat.
 */
export const editReport = async (reportId, reportData) => {
    const token = localStorage.getItem('authToken');

    try {
        const response = await axios.put(`${API_URL}/report/${reportId}`, reportData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error al editar el report:', error.response?.data || error.message);
        throw error;
    }
};

/**
 * Eliminar un report que pertanyi a l'usuari actual.
 *
 * @param {number} reportId - ID del report a eliminar.
 * @returns {Promise<Object>} Missatge de confirmació.
 */
export const deleteReport = async (reportId) => {
    const token = localStorage.getItem('authToken');

    try {
        const response = await axios.delete(`${API_URL}/report/${reportId}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error al eliminar el report:', error.response?.data || error.message);
        throw error;
    }
};

/**
 * Eliminar un report per ID (funció d'administrador).
 *
 * @param {number} reportId - ID del report a eliminar.
 * @returns {Promise<Object>} Missatge de confirmació.
 */
export const deleteReportByAdmin = async (reportId) => {
    const token = localStorage.getItem('authToken');

    try {
        const response = await axios.delete(`${API_URL}/report/a/${reportId}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error al eliminar el report (admin):', error.response?.data || error.message);
        throw error;
    }
};
