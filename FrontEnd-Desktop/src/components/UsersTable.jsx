import React, { useState, useEffect } from 'react';
import { fetchUsers } from '../services/ListUsersService';
import { useNavigate } from 'react-router-dom';

import '../css/userTable.css';

/**
 * UserTable component displays a table with a list of users, including their details
 * and an option to navigate to edit each user.
 *
 * This component fetches user data from the backend and displays it in a styled table.
 * Each user has an "Editar" button to navigate to the edit user page.
 *
 * @component
 * @returns {JSX.Element} 
 *
 * @example
 * // Example usage:
 * <UserTable />
 *
 * @author Enric Nanot Melchor
 */
export const UserTable = () => {
    const [users, setUsers] = useState([]);
    const navigate = useNavigate();

    /**
     * Fetches the list of users from the backend and updates the state.
     */
    useEffect(() => {
        const getUsers = async () => {
            try {
                const data = await fetchUsers();
                setUsers(data);
            } catch (error) {
                console.error('Error al obtener los usuarios:', error);
            }
        };

        getUsers();
    }, []);

    /**
     * Handles navigation to the edit user page.
     *
     * @param {number} userId 
     */
    const handleEdit = (userId) => {
        console.log(userId);
        navigate(`/editUser/${userId}`);
    };

    return (
        <div className="centered-container">
            <h2>User List</h2>
            <div className="UserTable">
                <table border="10" cellPadding="10" cellSpacing="0">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Role</th>
                            <th>Reportado</th>
                            <th>Editar Usuario</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.map((user) => (
                            <tr key={user.id}>
                                <td>{user.id}</td>
                                <td>{user.username}</td>
                                <td>{user.email}</td>
                                <td>{user.firstName || '-'}</td>
                                <td>{user.lastName || '-'}</td>
                                <td>{user.role}</td>
                                <td>{user.active ? 'Yes' : 'No'}</td>
                                <td>
                                    <button onClick={() => handleEdit(user.id)}>Editar</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default UserTable;
