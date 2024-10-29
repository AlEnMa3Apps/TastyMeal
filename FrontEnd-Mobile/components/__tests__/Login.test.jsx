import React from 'react'
import { render, fireEvent } from '@testing-library/react-native'
import LoginForm from '../../app/(auth)/login'
import { Alert } from 'react-native'

// Mock de AsyncStorage
jest.mock('@react-native-async-storage/async-storage', () => ({
  setItem: jest.fn(),
  getItem: jest.fn(),
  removeItem: jest.fn(),
  clear: jest.fn()
}));

// Mock de Alert
jest.spyOn(Alert, 'alert')

// Mock de useAuth
jest.mock('../../context/AuthContext', () => ({
	useAuth: () => ({
		login: jest.fn()
	})
}))

describe('LoginForm', () => {
	it('muestra un error si los campos están vacíos al presionar el botón de login', () => {
		const { getByText } = render(<LoginForm />)

		// Obtenemos el botón de login
		const loginButton = getByText('Login')

		// Simulamos el evento de presionar el botón sin ingresar datos
		fireEvent.press(loginButton)

		// Verificamos que se muestra el mensaje de error
		expect(Alert.alert).toHaveBeenCalledWith('Error', 'Please fill in all the fields.')
	})

	it('muestra un error si la contraseña es menor a 4 caracteres', () => {
		const { getByText, getByPlaceholderText } = render(<LoginForm />)

		// Ponemos un nombre de usuario válido y una contraseña corta
		fireEvent.changeText(getByPlaceholderText('Enter your username'), 'usuario')
		fireEvent.changeText(getByPlaceholderText('Enter your password'), '123')

		// Obtenemos el botón de login
		const loginButton = getByText('Login')

		// Simulamos el evento de presionar el botón
		fireEvent.press(loginButton)

		// Verificamos que se muestra el mensaje de error
		expect(Alert.alert).toHaveBeenCalledWith('Error', 'Password must be at least 4 characters long.')
	})
})
