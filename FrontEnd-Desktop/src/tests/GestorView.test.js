import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import GestorView from '../pages/gestorView';
/**
 * Test suite for the GestorView component.
 * Verifies that the component renders correctly without crashing.
 */
describe('GestorView Component', () => {

    /**
     * Test to check if the GestorView component renders without crashing.
     * It uses the MemoryRouter to simulate React Router context and renders the GestorView component.
     * The test checks if the "Vista de gestor" text is present in the document.
     */
    it('renders without crashing', () => {
        const { getByText } = render(
            <MemoryRouter>
                <GestorView />
            </MemoryRouter>
        );
        expect(getByText('Vista de gestor')).toBeInTheDocument();
    });
});
