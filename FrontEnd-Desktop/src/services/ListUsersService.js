import axios from 'axios';

/**
 * Fetches the list of users from the backend API.
 *
 * This function retrieves all users from the server, using the authentication token
 * stored in the browser's `localStorage`. If the request is successful, it returns
 * the data received from the API. If there is an error, it logs the error details
 * and rethrows the exception.
 *
 * @async
 * @function fetchUsers
 * @returns {Promise<Array>}
 * 
 * @throws {Error} 
 *
 * @example
 * // Example usage:
 * fetchUsers()
 *   .then(users => console.log(users))
 *   .catch(error => console.error(error));
 *
 * @author Enric Nanot Melchor
 */
export const fetchUsers = async () => {
    const token = localStorage.getItem('authToken');

    return axios.get('https://localhost:8443/api/users', {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then((response) => {

            return response.data;
        })
        .catch((error) => {
            console.error('fetch users error');


            if (error.response && error.response.data) {
                console.error('Server error:', error.response.data);
            } else {
                console.error('Unknown error.');
            }

            throw error;
        });
};
