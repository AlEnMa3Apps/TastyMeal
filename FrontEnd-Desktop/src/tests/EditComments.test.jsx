import React from 'react';
import { render, screen, act, fireEvent } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import EditComments from '../pages/editComments';

/**
 * Mock de los servicios utilizados por el componente
 */
jest.mock('../services/commentsService', () => ({
    fetchCommentsForRecipe: jest.fn(() =>
        Promise.resolve([
            { id: 1, comment: 'Comentario de prueba 1' },
            { id: 2, comment: 'Comentario de prueba 2' },
        ])
    ),
    addCommentToRecipe: jest.fn((recipeId, commentData) =>
        Promise.resolve({ id: 3, comment: commentData.comment })
    ),
    editComment: jest.fn((id, commentData) =>
        Promise.resolve({ id, comment: commentData.comment })
    ),
    deleteComment: jest.fn((id) => Promise.resolve()),
}));

/**
 * Test suite for the EditComments component.
 */
describe('EditComments Component', () => {
    const renderComponent = (recipeId) => {
        return render(
            <MemoryRouter initialEntries={[`/editComments/${recipeId}`]}>
                <Routes>
                    <Route path="/editComments/:recipeId" element={<EditComments />} />
                </Routes>
            </MemoryRouter>
        );
    };

    /**
     * Test to verify the component renders without crashing and displays key elements.
     */
    it('renders without crashing and displays key elements', async () => {
        await act(async () => {
            renderComponent(123);
        });

        expect(screen.getByText('Editar Comentarios')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('Añade un nuevo comentario')).toBeInTheDocument();
        expect(screen.getByText('Agregar Comentario')).toBeInTheDocument();
    });

    /**
     * Test to verify that comments are fetched and displayed correctly.
     */
    it('fetches and displays comments correctly', async () => {
        await act(async () => {
            renderComponent(123);
        });

        expect(screen.getByText('Comentario de prueba 1')).toBeInTheDocument();
        expect(screen.getByText('Comentario de prueba 2')).toBeInTheDocument();
    });

    /**
     * Test to verify adding a new comment updates the list.
     */
    it('adds a new comment and displays it', async () => {
        await act(async () => {
            renderComponent(123);
        });

        const textArea = screen.getByPlaceholderText('Añade un nuevo comentario');
        const addButton = screen.getByText('Agregar Comentario');


        fireEvent.change(textArea, { target: { value: 'Nuevo comentario' } });
        fireEvent.click(addButton);


        expect(await screen.findByText('Nuevo comentario')).toBeInTheDocument();
    });

});
