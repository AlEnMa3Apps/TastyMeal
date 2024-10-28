import axios from 'axios';

/**
 * Sends a login request to the authentication server.
 * This function sends the user's login credentials to the server and returns the response data.
 * 
 * @async
 * @function
 * @param {Object} loginData - The login data object containing username and password.
 * @param {string} loginData.username - The username entered by the user.
 * @param {string} loginData.password - The password entered by the user.
 * @returns {Promise<Object>} The server response containing authentication details such as token and user role.
 * @throws {Error} If the login fails or there is a server error.
 * 
 * @example
 * const loginData = { username: 'user1', password: 'password123' };
 * fetchlogin(loginData)
 *   .then(response => console.log(response))
 *   .catch(error => console.error(error));
 * 
 * @author Enric Nanot Melchor
 */
export const fetchlogin = async (loginData) => {
    console.log(loginData);
    return axios.post('http://localhost:8080/auth/login', loginData)
        .then((response) => {
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
