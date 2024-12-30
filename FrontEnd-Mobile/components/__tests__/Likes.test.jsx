import React from 'react'
import { render, waitFor, fireEvent } from '@testing-library/react-native'
import LikesScreen from '../../app/(tabs)/likes'
import api from '../../api/api'

// 1. Mockear la API
jest.mock('../../api/api', () => ({
  get: jest.fn()
}))

// 2. Mockear useRouter y useIsFocused
// - useRouter: devuelva un objeto con .push
// - useIsFocused: devuelva true
jest.mock('expo-router', () => ({
  useRouter: () => ({
    push: jest.fn()
  })
}))
jest.mock('@react-navigation/native', () => ({
  // Solo necesitamos mockear useIsFocused para devolver true
  useIsFocused: () => true
}))

describe('LikesScreen - favoritos', () => {
  beforeEach(() => {
    jest.clearAllMocks()
  })

  test('muestra la lista de recetas favoritas cuando hay favoritos', async () => {
    // Simulamos que /api/recipes/favorite devuelve [1, 2]
    api.get
      .mockResolvedValueOnce({ data: [1, 2] }) // primera llamada al endpoint /api/recipes/favorite
      // para la segunda (GET /api/recipe/1)
      .mockResolvedValueOnce({ data: { id: 1, title: 'Receta 1', imageUrl: 'http://image1.jpg', description: 'Desc 1' } })
      // para la tercera (GET /api/recipe/2)
      .mockResolvedValueOnce({ data: { id: 2, title: 'Receta 2', imageUrl: 'http://image2.jpg', description: 'Desc 2' } })

    const { getByText, findByText, queryByText } = render(<LikesScreen />)

    // Comprobamos que carga (loading)
    // Esperamos que aparezcan las recetas
    // Por si tarde un poco en resolverse, usamos waitFor
    await waitFor(async () => {
      // Deben aparecer las recetas con su título
      expect(getByText('Receta 1')).toBeTruthy()
      expect(getByText('Receta 2')).toBeTruthy()
      // No debe aparecer el texto de "No tienes recetas favoritas."
      expect(queryByText('No tienes recetas favoritas.')).toBeNull()
    })
  })

  test('muestra el mensaje de no favoritos cuando la lista está vacía', async () => {
    // Devuelve un array vacío para /api/recipes/favorite
    api.get.mockResolvedValueOnce({ data: [] })

    const { findByText } = render(<LikesScreen />)

    // Esperamos a que aparezca el texto indicando que no hay favoritos
    expect(await findByText('No tienes recetas favoritas.')).toBeTruthy()
  })
})