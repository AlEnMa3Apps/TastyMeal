import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import AdminView from '../pages/adminView';

/**
 * Test suite for the AdminView component.
 * Verifies that the component renders correctly without crashing.
 */
describe('AdminView Component', () => {

    /**
     * Test to check if the AdminView component renders without crashing.
     * It uses the MemoryRouter to simulate React Router context and renders the AdminView component.
     * The test checks if the "Admin View" text is present in the document.
     */
    it('renders without crashing', () => {
        const { getByText } = render(
            <MemoryRouter>
                <AdminView />
            </MemoryRouter>
        );
        expect(getByText('Admin View')).toBeInTheDocument();
    });
});
