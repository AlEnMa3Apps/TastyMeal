import axios from 'axios';

/**
 * Sends a login request to the authentication server.
 * This function sends the user's login credentials to the server and returns the response data.
 * 
 * @async
 * @function
 * @param {Object} loginData
 * @param {string} loginData.username
 * @param {string} loginData.password 
 * @returns {Promise<Object>} 
 * @throws {Error} 
 * 
 * @example
 * const loginData = { username: 'user1', password: 'password123' };
 * fetchlogin(loginData)
 *   .then(response => console.log(response))
 *   .catch(error => console.error(error));
 * 
 * @author Enric Nanot Melchor
 */

// Permitir certificados autofirmados solo en entorno de desarrollo
// if (process.env.NODE_ENV === 'development') {
//     process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';
// }
export const fetchlogin = async (loginData) => {
    console.log(loginData);
    return axios.post('https://localhost:8443/auth/login', loginData)
        .then((response) => {
            const { token } = response.data;

            localStorage.setItem('authToken', token);
            return response.data;
        })
        .catch((error) => {
            console.error('Login error.');
            if (error.response && error.response.data) {
                console.error('Server error:', error.response.data);
            } else {
                console.error('Unknown error.');
            }
            throw error;
        });
};
