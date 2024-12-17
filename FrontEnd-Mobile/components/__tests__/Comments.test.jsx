import React from 'react'
import { render, fireEvent, waitFor } from '@testing-library/react-native'
import { UserProvider } from '../../context/UserContext'
import api from '../../api/api'
import RecipeDetails from '../../app/(tabs)/(home)/[id]'

// Mock de la API
jest.mock('../../api/api', () => ({
  get: jest.fn(),
  post: jest.fn(),
  delete: jest.fn(),
  put: jest.fn()
}))

// Mock de expo-router y useLocalSearchParams
jest.mock('expo-router', () => ({
  useLocalSearchParams: () => ({ id: '123' }) // Simula el ID de la receta
}))

// Función para renderizar con contexto
const renderWithContext = (component) => {
  return render(<UserProvider>{component}</UserProvider>)
}

describe('RecipeDetails - Comentarios', () => {
  beforeEach(() => {
    jest.clearAllMocks()
  })

  test('renderiza los comentarios correctamente', async () => {
    // Primera llamada: datos de la receta
    api.get.mockResolvedValueOnce({
      data: {
        title: 'Test Recipe',
        imageUrl: 'https://example.com/image.jpg',
        description: 'A tasty test recipe',
        ingredients: 'Test ingredients',
        cookingTime: 30,
        numPersons: 2,
        categoryName: 'Dessert'
      }
    })

    // Segunda llamada: comentarios
    api.get.mockResolvedValueOnce({
      data: [
        { id: 1, author: 'User1', comment: 'Delicious recipe!' },
        { id: 2, author: 'User2', comment: 'Easy to follow.' }
      ]
    })

    const { findByText } = renderWithContext(<RecipeDetails />)

    // Esperamos a que aparezca el título de la receta (indica que se cargó)
    await findByText('Test Recipe')

    // Ahora esperamos a los comentarios
    await waitFor(async () => {
      expect(await findByText('User1: Delicious recipe!')).toBeTruthy()
      expect(await findByText('User2: Easy to follow.')).toBeTruthy()
    })
  })

  test('permite añadir un comentario', async () => {
    // Primera llamada: datos de la receta
    api.get.mockResolvedValueOnce({
      data: {
        title: 'Test Recipe',
        imageUrl: 'https://example.com/image.jpg',
        description: 'A tasty test recipe',
        ingredients: 'Test ingredients',
        cookingTime: 30,
        numPersons: 2,
        categoryName: 'Dessert'
      }
    })

    // Segunda llamada: comentarios inicialmente vacíos
    api.get.mockResolvedValueOnce({
      data: []
    })

    // Mock de la respuesta al añadir un comentario
    api.post.mockResolvedValueOnce({
      data: { id: 3, author: 'TestUser', comment: 'New Comment' }
    })

    // Tercera llamada: comentarios después de añadir uno nuevo
    api.get.mockResolvedValueOnce({
      data: [{ id: 3, author: 'TestUser', comment: 'New Comment' }]
    })

    const { findByText, getByPlaceholderText, getByText } = renderWithContext(<RecipeDetails />)

    // Esperar a que aparezca el título, indicando que la receta se cargó
    await findByText('Test Recipe')

    // Escribimos un nuevo comentario
    const commentInput = getByPlaceholderText('Leave a comment...')
    fireEvent.changeText(commentInput, 'New Comment')

    // Presionamos el botón de comentar
    const addButton = getByText('Comment')
    fireEvent.press(addButton)

    // Verificamos que se haya hecho la llamada POST con los datos correctos
    await waitFor(() => {
      expect(api.post).toHaveBeenCalledWith('/api/recipe/123/comment', {
        comment: 'New Comment'
      })
    })

    // Verificar que el nuevo comentario se muestre
    await waitFor(async () => {
      expect(await findByText('TestUser: New Comment')).toBeTruthy()
    })
  })
})