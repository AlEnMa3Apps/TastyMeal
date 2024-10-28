import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Login from '../pages/login';

/**
 * Test suite for the Login component.
 * This suite checks that the Login component renders without crashing.
 */
describe('Login Component', function () {

    /**
     * Test to verify that the Login component renders without crashing.
     * It uses the MemoryRouter to simulate React Router context and renders the Login component.
     * The test checks if the component container is defined.
     */
    it('should render without crashing', function () {

        const { container } = render(
            <MemoryRouter>
                <Login />
            </MemoryRouter>
        );
        expect(container).toBeDefined();
    });
});
