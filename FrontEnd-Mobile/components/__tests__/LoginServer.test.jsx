import React from 'react'
import { render, fireEvent, waitFor } from '@testing-library/react-native'
import LoginForm from '../../app/(auth)/login'
import { AuthProvider } from '../../context/AuthContext'
import { UserProvider } from '../../context/UserContext'
import { Alert } from 'react-native'

// Crea una función wrapper para proveer el contexto de autenticación
const renderWithContext = (component) => {
	return render(
		<AuthProvider>
			<UserProvider>{component}</UserProvider>
		</AuthProvider>
	)
}

describe('LoginScreen - Prueba de Integración', () => {
	beforeEach(() => {
		jest.clearAllMocks()
		jest.spyOn(Alert, 'alert')
	})

	test('muestra un mensaje de error si el backend está desconectado', async () => {
		const { getByTestId } = renderWithContext(<LoginForm />)

		// Introduce credenciales de prueba
		fireEvent.changeText(getByTestId('username-input'), 'Manuel')
		fireEvent.changeText(getByTestId('password-input'), '1234')

		// Envía el formulario de login
		fireEvent.press(getByTestId('login-button'))

		// Espera y verifica que se muestra un mensaje de error
		await waitFor(() => {
			expect(Alert.alert).toHaveBeenCalledWith('Error', 'Wrong credentials.')
		})
	})
})
