import React from 'react'
import { render, fireEvent } from '@testing-library/react-native'
import RecipeScreen from '../../app/(tabs)/(recipe)/index'
import { router } from 'expo-router'

// Mock de la navegación de Expo Router
jest.mock('expo-router', () => ({
	router: {
		push: jest.fn()
	}
}))

/**
 * Grupo de pruebas para el componente RecipeScreen.
 */
describe('RecipeScreen', () => {
	/**
	 * Test para verificar que los botones se renderizan correctamente.
	 * Este test asegura que los textos de los botones "Create Recipe", "Edit Recipe" y "Delete Recipe" están presentes en la pantalla.
	 */
	test('renderiza los botones correctamente', () => {
		const { getByText } = render(<RecipeScreen />)
		expect(getByText('Create Recipe')).toBeTruthy()
		expect(getByText('Edit Recipe')).toBeTruthy()
		expect(getByText('Delete Recipe')).toBeTruthy()
	})

	/**
	 * Test para verificar la navegación a la pantalla "Create Recipe".
	 * Simula un evento de pulsar el botón "Create Recipe" y verifica que se navega a la ruta esperada.
	 */
	test('navega a la pantalla de crear receta al presionar el botón', () => {
		const { getByTestId } = render(<RecipeScreen />)
		const createButton = getByTestId('create-recipe')
		fireEvent.press(createButton)
		expect(router.push).toHaveBeenCalledWith('/(recipe)/create-recipe')
	})

	/**
	 * Test para verificar la navegación a la pantalla "Edit Recipe".
	 * Simula un evento de pulsar el botón "Edit Recipe" y verifica que se navega a la ruta esperada.
	 */
	it('navega a la pantalla de editar receta al presionar el botón', () => {
		const { getByTestId } = render(<RecipeScreen />)
		const editButton = getByTestId('edit-recipe')
		fireEvent.press(editButton)
		expect(router.push).toHaveBeenCalledWith('/(recipe)/edit-recipe')
	})

	/**
	 * Test para verificar la navegación a la pantalla "Delete Recipe".
	 * Simula un evento de pulsar el botón "Delete Recipe" y verifica que se navega a la ruta esperada.
	 */
	it('navega a la pantalla de eliminar receta al presionar el botón', () => {
		const { getByTestId } = render(<RecipeScreen />)
		const deleteButton = getByTestId('delete-recipe')
		fireEvent.press(deleteButton)
		expect(router.push).toHaveBeenCalledWith('/(recipe)/delete-recipe')
	})
})
