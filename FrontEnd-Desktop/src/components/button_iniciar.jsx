import React from 'react';
import Button from '@mui/material/Button';

/**
 * Component that renders a button to initiate a login process. 
 * This button triggers the `fetchLogin` function located in the `login` component.
 *
 * @component
 * @returns {JSX.Element} A button that triggers the `fetchLogin` function on click.
 *
 * @author Enric Nanot Melchor
 *
 * @example
 * // Example of usage:
 * <Button_iniciar />
 */
export function Button_iniciar() {

  /**
   * Function that handles the button click and triggers the `fetchLogin` function
   * located in the `login` component. The actual logic of this function is not
   * implemented in this component.
   */
  const handleHomeClick = () => {

  };

  return (
    <Button onClick={handleHomeClick} type="submit">
      Iniciar sesión
    </Button>
  );
}
