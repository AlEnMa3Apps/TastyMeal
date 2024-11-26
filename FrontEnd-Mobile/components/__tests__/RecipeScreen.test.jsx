import React from 'react'
import { render, fireEvent } from '@testing-library/react-native'
import RecipeScreen from '../../app/(tabs)/(recipe)/index'
import { router } from 'expo-router'

jest.mock('expo-router', () => ({
	router: {
		push: jest.fn()
	}
}))

describe('RecipeScreen', () => {
	test('renderiza los botones correctamente', () => {
		const { getByText } = render(<RecipeScreen />)
		expect(getByText('Create Recipe')).toBeTruthy()
		expect(getByText('Edit Recipe')).toBeTruthy()
		expect(getByText('Delete Recipe')).toBeTruthy()
	})

	test('navega a la pantalla de crear receta al presionar el botón', () => {
		const { getByTestId } = render(<RecipeScreen />)
		const createButton = getByTestId('create-recipe')
		fireEvent.press(createButton)
		expect(router.push).toHaveBeenCalledWith('/(recipe)/create-recipe')
	})

	it('navega a la pantalla de editar receta al presionar el botón', () => {
		const { getByTestId } = render(<RecipeScreen />)
		const editButton = getByTestId('edit-recipe')
		fireEvent.press(editButton)
		expect(router.push).toHaveBeenCalledWith('/(recipe)/edit-recipe')
	})

	it('navega a la pantalla de eliminar receta al presionar el botón', () => {
		const { getByTestId } = render(<RecipeScreen />)
		const deleteButton = getByTestId('delete-recipe')
		fireEvent.press(deleteButton)
		expect(router.push).toHaveBeenCalledWith('/(recipe)/delete-recipe')
	})
})
