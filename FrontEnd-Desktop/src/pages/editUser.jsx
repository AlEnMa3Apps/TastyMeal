import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchUserById, updateUser, DeleteUser } from '../services/userService';
import '../css/editUser.css';

/**
 * EditUser component allows editing and deleting a specific user.
 *
 * This component fetches the user details based on the `userId` from the URL,
 * allows the user to update the details, or delete the user entirely.
 *
 * @component
 * @returns {JSX.Element} 
 *
 * @example
 * // Example usage:
 * <EditUser />
 *
 * @author Enric Nanot Melchor
 */
const EditUser = () => {
    const { userId } = useParams();
    const navigate = useNavigate();

    /**
     * State to store the user details.
     * @typedef {Object} User
     * @property {string} id 
     * @property {string} username 
     * @property {string} email 
     * @property {string} firstName
     * @property {string} lastName 
     * @property {string} role 
     * @property {boolean} active 
     */
    const [user, setUser] = useState({
        id: '',
        username: '',
        email: '',
        firstName: '',
        lastName: '',
        role: '',
        active: false,
    });

    /**
     * Fetches the user details when the component mounts or when `userId` changes.
     * Updates the state with the fetched user data.
     *
     * @function useEffect
     */
    useEffect(() => {
        const getUser = async () => {
            try {
                const data = await fetchUserById(userId);
                setUser(data);
            } catch (error) {
                console.error('Error al obtener los datos del usuario:', error);
            }
        };
        getUser();
    }, [userId]);

    /**
     * Handles changes in the form inputs and updates the user state.
     *
     * @function handleChange
     * @param {Object} e 
     * @param {string} e.target.name 
     * @param {string} e.target.value 
     * @param {string} e.target.type 
     */
    const handleChange = (e) => {
        const { name, value, type } = e.target;
        setUser((prevUser) => ({
            ...prevUser,
            [name]: type === 'checkbox' ? value === 'true' : value,
        }));
    };

    /**
     * Submits the updated user data to the backend.
     * Displays a success or error message based on the result.
     *
     * @function handleSubmit
     * @param {Object} e 
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateUser(userId, user);
            alert('Cambios realizados');
            navigate('/admin');
        } catch (error) {
            console.error('Error al actualizar el usuario:', error);
            alert('Hubo un problema al actualizar el usuario');
        }
    };

    /**
     * Deletes the user after user confirmation.
     * Navigates back to the admin page upon success.
     *
     * @function deleteUser
     */
    const deleteUser = async () => {
        if (window.confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
            try {
                await DeleteUser(userId);
                navigate('/admin');
            } catch (error) {
                console.error('Error al eliminar el usuario', error);
                alert('No ha sido posible eliminar a este usuario');
            }
        }
    };

    return (
        <div className="form-container">
            <h1>Edit User</h1>
            <form onSubmit={handleSubmit} className="edit-form">
                <div>
                    <label htmlFor="id">ID:</label>
                    <input id="id" type="text" name="id" value={user.id} readOnly />
                </div>
                <div>
                    <label htmlFor="username">Username:</label>
                    <input
                        id="username"
                        type="text"
                        name="username"
                        value={user.username}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        id="email"
                        type="email"
                        name="email"
                        value={user.email}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="firstName">First Name:</label>
                    <input
                        id="firstName"
                        type="text"
                        name="firstName"
                        value={user.firstName}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="lastName">Last Name:</label>
                    <input
                        id="lastName"
                        type="text"
                        name="lastName"
                        value={user.lastName}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="role">Role:</label>
                    <input
                        id="role"
                        type="text"
                        name="role"
                        value={user.role}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label htmlFor="active">Reportado:</label>
                    <select
                        id="active"
                        name="active"
                        value={user.active}
                        onChange={handleChange}
                    >
                        <option value={true}>Sí</option>
                        <option value={false}>No</option>
                    </select>
                </div>
                <div className="button-group">
                    <button type="submit">Guardar cambios</button>
                    <button type="button" onClick={() => navigate('/admin')}>
                        Cancelar
                    </button>
                    <button type="button" onClick={deleteUser}>
                        Borrar Usuario
                    </button>
                </div>
            </form>
        </div>
    );
};

export default EditUser;
