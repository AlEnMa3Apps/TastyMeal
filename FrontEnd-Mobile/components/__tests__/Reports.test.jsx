import React from 'react'
import { render, waitFor, fireEvent } from '@testing-library/react-native'
import ReportRecipe from '../../app/(tabs)/(home)/report-recipe' // Ajusta la ruta real de tu componente
import api from '../../api/api'
import { UserProvider } from '../../context/UserContext'

// Mock de la API
jest.mock('../../api/api', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}))

// Mock de expo-router: para useLocalSearchParams -> recipeId
jest.mock('expo-router', () => ({
  useLocalSearchParams: () => ({ recipeId: '123' })
}))

// Para renderizar el contexto de usuario
const renderWithUserContext = (ui, { userValue } = {}) => {
  return render(
    <UserProvider value={userValue}>
      {ui}
    </UserProvider>
  )
}

describe('ReportRecipe screen', () => {
  beforeEach(() => {
    jest.clearAllMocks()
  })

  test('carga los reports y los muestra en la lista', async () => {
    // Simula que GET /api/recipe/123/reports retorna tres reports
    api.get.mockResolvedValueOnce({
      data: [
        { id: 1, report: 'Error en ingredientes' },
        { id: 2, report: 'Receta muy confusa' },
        { id: 3, report: 'Falta información' }
      ]
    })

    const { getByText, findByText, queryByText } = renderWithUserContext(<ReportRecipe />)

    // Al inicio, se mostrará el spinner de carga
    expect(getByText('Cargando reports...')).toBeTruthy()

    // Esperamos a que el spinner desaparezca y aparezcan los items
    await waitFor(() => {
      // Ya no debe estar "Cargando reports..."
      expect(queryByText('Cargando reports...')).toBeNull()
      // Deben aparecer los 3 reports en la pantalla
      expect(getByText('Error en ingredientes')).toBeTruthy()
      expect(getByText('Receta muy confusa')).toBeTruthy()
      expect(getByText('Falta información')).toBeTruthy()
    })
  })

  test('crea un nuevo reporte y se actualiza la lista', async () => {
    // 1) Llamada inicial GET => sin reports
    api.get.mockResolvedValueOnce({ data: [] })

    // 2) Al crear un reporte (POST => /api/recipe/123/report) no necesita response body
    api.post.mockResolvedValueOnce({})

    // 3) Luego, se vuelve a llamar GET => ahora con 1 reporte
    api.get.mockResolvedValueOnce({
      data: [{ id: 10, report: 'Reporte nuevo' }]
    })

    const { getByPlaceholderText, getByText, findByText } = renderWithUserContext(<ReportRecipe />)

    // Esperar a que se cargue (o no haya reports):
    // "Cargando reports..." se muestra brevemente
    // y luego vemos que no hay items
    await waitFor(() => {
      // Termina la carga inicial
      expect(getByText('Crear nuevo reporte:')).toBeTruthy()
    })

    // Escribimos un nuevo reporte
    const input = getByPlaceholderText('Descripción del reporte...')
    fireEvent.changeText(input, 'Reporte nuevo')

    // Presionamos el botón de crear
    fireEvent.press(getByText('Crear Report'))

    // Verificamos que se haya llamado al POST con el body correcto
    await waitFor(() => {
      expect(api.post).toHaveBeenCalledWith('/api/recipe/123/report', { report: 'Reporte nuevo' })
    })

    // Esperamos a que se vuelva a hacer el GET y muestre el nuevo reporte
    await findByText('Reporte nuevo') // Debe aparecer el nuevo reporte
  })
})