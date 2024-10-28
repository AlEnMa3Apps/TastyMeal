import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import ContactView from '../pages/contact';

/**
 * Test suite for the ContactView component.
 * Verifies that the component renders correctly without crashing.
 */
describe('ContactView Component', () => {

    /**
     * Test to check if the ContactView component renders without crashing.
     * It uses the MemoryRouter to simulate React Router context and renders the ContactView component.
     * The test checks if the "Contact" text is present in the document.
     */
    it('renders without crashing', () => {
        const { getByText } = render(
            <MemoryRouter>
                <ContactView />
            </MemoryRouter>
        );
        expect(getByText('Contact')).toBeInTheDocument();
    });
});
