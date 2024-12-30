import React from 'react'
import { render, waitFor, fireEvent } from '@testing-library/react-native'
import EventDetails from '../../app/(tabs)/(events)/[id]'
import api from '../../api/api'

// 1. Mockear la API con jest.mock
jest.mock('../../api/api', () => ({
	get: jest.fn(),
	post: jest.fn(),
	delete: jest.fn()
}))

// 2. Mockear expo-router: useLocalSearchParams => { id: '10' }
jest.mock('expo-router', () => ({
	useLocalSearchParams: () => ({ id: '10' })
}))

describe('EventDetails Screen', () => {
	beforeEach(() => {
		jest.clearAllMocks()
	})

	test('carga el evento y lo muestra correctamente', async () => {
		// Mock al obtener el evento (GET /api/event/10)
		api.get.mockResolvedValueOnce({
			data: {
				id: 10,
				title: 'Concierto de Rock',
				date: '2025-05-01',
				duration: '3h',
				description: 'Un evento musical increíble',
				categoryName: 'Música'
			}
		})

		// Mock para la lista de registrados (GET /api/events/registered)
		// En este caso, el usuario NO está registrado en el evento 10
		api.get.mockResolvedValueOnce({
			data: [2, 5] // p.ej. el usuario está registrado en el evento 2 y 5
		})

		const { getByText, queryByText, findByText } = render(<EventDetails />)

		// Loading
		expect(getByText('Loading event details...')).toBeTruthy()

		// Esperamos a que se cargue el evento
		await waitFor(() => {
			// “Concierto de Rock” debería aparecer
			expect(getByText('Título: Concierto de Rock')).toBeTruthy()
			// Comprobamos que el texto "No estás apuntado a este evento" se muestre
			expect(getByText('No estás apuntado a este evento')).toBeTruthy()

			// Ya no debería estar el "Loading event details..."
			expect(queryByText('Loading event details...')).toBeNull()
		})
	})

	test('permite registrarse y desregistrarse del evento', async () => {
		// 1) GET /api/event/10 => datos del evento
		api.get.mockResolvedValueOnce({
			data: {
				id: 10,
				title: 'Feria de Tecnología',
				date: '2025-10-10',
				duration: '5h',
				description: 'Un evento para los amantes de la tecnología',
				categoryName: 'Tech'
			}
		})
		// 2) GET /api/events/registered => el usuario NO está registrado en el evento 10
		api.get.mockResolvedValueOnce({ data: [1, 2] })

		const { getByText, queryByText, findByText, getByRole, getByTestId } = render(<EventDetails />)

		// Esperamos que cargue
		await findByText('Título: Feria de Tecnología') // Indica que ya se cargó el evento

		// Comprueba que inicialemte muestre "No estás apuntado a este evento"
		expect(getByText('No estás apuntado a este evento')).toBeTruthy()

		// Al hacer click en el ícono, registramos al usuario (POST /api/event/10/register)
		api.post.mockResolvedValueOnce({}) // No necesitamos response body
		fireEvent.press(getByTestId('registerButton'))

		await waitFor(() => {
			// Se debió llamar a POST /api/event/10/register
			expect(api.post).toHaveBeenCalledWith('/api/event/10/register')
		})

		// Ahora el texto debe cambiar a "Ya estás apuntado a este evento"
		// Y se hace un setIsRegistered(true) en la lógica
		expect(getByText('Ya estás apuntado a este evento')).toBeTruthy()

		// DES-registrarse (DELETE /api/event/10/unregister)
		api.delete.mockResolvedValueOnce({})
		fireEvent.press(getByTestId('registerButton'))

		await waitFor(() => {
			// Se debió llamar a DELETE /api/event/10/unregister
			expect(api.delete).toHaveBeenCalledWith('/api/event/10/unregister')
		})

		// Vuelve al estado "No estás apuntado..."
		expect(getByText('No estás apuntado a este evento')).toBeTruthy()
	})
})
