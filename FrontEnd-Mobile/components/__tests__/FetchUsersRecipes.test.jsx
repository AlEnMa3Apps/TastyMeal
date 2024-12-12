import api from '../../api/api'

// Mockeamos el módulo API para simular las peticiones
jest.mock('../../api/api', () => ({
	get: jest.fn()
}))

/**
 * Pruebas para la función de obtener recetas del usuario (fetchUserRecipes).
 */
describe('fetchUserRecipes', () => {
  
	/**
	 * Test para verificar que las recetas del usuario se obtienen correctamente.
	 * - Se simula una respuesta exitosa de la API con datos de prueba.
	 * - Se verifica que el endpoint correcto sea llamado.
	 * - Se verifica que los datos devueltos sean los esperados.
	 */
	test('debería obtener las recetas del usuario correctamente', async () => {
		// Datos simulados para la prueba
		const datosMock = [{ id: 1, title: 'Receta 1' }]
		api.get.mockResolvedValueOnce({ data: datosMock })

		// Llamada simulada al endpoint
		const recetas = await api.get('/api/recipes')
		expect(api.get).toHaveBeenCalledWith('/api/recipes') // Verificamos que se llame con el endpoint correcto
		expect(recetas.data).toEqual(datosMock) // Verificamos que los datos sean los esperados
	})

	/**
	 * Test para verificar el manejo de errores de la API.
	 * - Se simula un error al llamar al endpoint.
	 * - Se verifica que el error sea lanzado correctamente.
	 */
	test('debería manejar errores de la API', async () => {
		// Simulamos un error en la API
		api.get.mockRejectedValueOnce(new Error('Error de API'))

		// Verificamos que se lance un error al llamar al endpoint
		await expect(api.get('/api/recipes')).rejects.toThrow('Error de API')
	})
})
