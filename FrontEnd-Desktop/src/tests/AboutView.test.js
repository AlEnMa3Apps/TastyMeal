import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import AboutView from '../pages/about';

/**
 * Test suite for the AboutView component.
 * Verifies that the component renders correctly without crashing.
 */
describe('AboutView Component', () => {

    /**
     * Test to check if the AboutView component renders without crashing.
     * It uses the MemoryRouter to simulate React Router context and renders the AboutView component.
     * The test checks if the "About" text is present in the document.
     */
    it('renders without crashing', () => {
        const { getByText } = render(
            <MemoryRouter>
                <AboutView />
            </MemoryRouter>
        );
        expect(getByText('About')).toBeInTheDocument();
    });
});
