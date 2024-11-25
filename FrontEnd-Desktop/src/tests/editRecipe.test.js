import React from 'react';
import { render, screen, act } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import EditRecipe from '../pages/editRecipes';

/**
 * Test suite for the EditRecipe component.
 * Verifies that the component renders correctly without crashing.
 */
describe('EditRecipe Component', () => {
    /**
     * Test to check if the EditRecipe component renders without crashing.
     * It uses the MemoryRouter to simulate React Router context and renders the EditRecipe component.
     * The test checks if the "Editar Receta" text is present in the document.
     */
    it('renders without crashing', async () => {
        await act(async () => {
            render(
                <MemoryRouter>
                    <EditRecipe />
                </MemoryRouter>
            );
        });

        expect(screen.getByText('Edit Recipe')).toBeInTheDocument();

        expect(screen.getByLabelText(/Título:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Descripción:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/URL de Imagen:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Tiempo de Cocción:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Número de Personas:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Ingredientes:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Categoría:/i)).toBeInTheDocument();
    });
});
