import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Home from '../pages/home';

/**
 * Test suite for the Home component.
 * Verifies that the component renders correctly without crashing.
 */
describe('Home Component', () => {

    /**
     * Test to check if the Home component renders without crashing.
     * It uses the MemoryRouter to simulate React Router context and renders the Home component.
     * The test checks if the "Tasty Meal" text is present in the document.
     */
    it('renders without crashing', () => {
        const { getByText } = render(
            <MemoryRouter>
                <Home />
            </MemoryRouter>
        );
        expect(getByText('Tasty Meal')).toBeInTheDocument();
    });
});
