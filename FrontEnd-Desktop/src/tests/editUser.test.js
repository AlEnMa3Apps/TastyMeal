import React from 'react';
import { render, screen, act } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import EditUser from '../pages/editUser';

jest.mock('../services/userService', () => ({
    fetchUserById: jest.fn().mockResolvedValue({
        id: 1,
        username: 'testuser',
        email: 'testuser@example.com',
        firstName: 'Test',
        lastName: 'User',
        role: 'ADMIN',
        active: true,
    }),
}));

/**
 * Test suite for the EditUser component.
 * Verifies that the component renders correctly without crashing.
 */
describe('EditUser Component', () => {
    /**
     * Test to check if the EditUser component renders without crashing.
     * It uses the MemoryRouter to simulate React Router context and renders the EditUser component.
     * The test checks if the "Editar Usuario" text is present in the document.
     */
    it('renders without crashing', async () => {
        await act(async () => {
            render(
                <MemoryRouter>
                    <EditUser />
                </MemoryRouter>
            );
        });


        expect(screen.getByText('Edit User')).toBeInTheDocument();


        expect(screen.getByLabelText(/Username:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Email:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/First Name:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Last Name:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Role:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Reportado:/i)).toBeInTheDocument();
    });
});
