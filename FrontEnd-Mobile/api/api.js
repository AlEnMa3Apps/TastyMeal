/**
 * Configuración de la instancia de Axios para las solicitudes HTTP.
 *
 * @fileoverview Configura la instancia de Axios con la URL base y el interceptor de solicitudes para manejar la autenticación.
 * @author Manuel García Nieto
 */

import axios from 'axios'
import AsyncStorage from '@react-native-async-storage/async-storage'
import { Platform } from 'react-native'

/**
 * URL base para las solicitudes HTTP a la API.
 *
 * Selecciona la URL apropiada dependiendo de la plataforma:
 * - En Android, se utiliza `10.0.2.2` para referirse al host local desde el emulador.
 * - En iOS, se utiliza `localhost` directamente.
 *
 * @constant {string} baseURL - La URL base para las solicitudes de API.
 */
const baseURL =
	Platform.OS === 'android'
		? 'http://10.0.2.2:8080' // Android
		: 'http://localhost:8080' // iOS

/**
 * Instancia de Axios configurada con la URL base.
 *
 * @type {AxiosInstance}
 */
const api = axios.create({
	baseURL: baseURL
})

// Endpoints públicos que no requieren autenticación
const publicEndpoints = ['/auth/register', '/auth/login']

/**
 * Interceptor de solicitudes para agregar el token de autenticación en los encabezados.
 *
 * Este interceptor se ejecuta antes de cada solicitud realizada con la instancia `api`.
 * Si existe un token almacenado en `AsyncStorage`, se agrega al encabezado `Authorization` de la solicitud.
 *
 * @async
 * @function
 * @param {AxiosRequestConfig} config - La configuración de la solicitud de Axios.
 * @returns {Promise<AxiosRequestConfig>} La configuración modificada de la solicitud.
 */
api.interceptors.request.use(
	async (config) => {
		// Verificar si el endpoint es público
		if (publicEndpoints.some((endpoint) => config.url.includes(endpoint))) {
			return config
		}

		// Obtener el token de AsyncStorage
		const token = await AsyncStorage.getItem('token')
		if (token) {
			config.headers.Authorization = `Bearer ${token}`
		}
		return config
	},
	/**
	 * Manejador de errores para el interceptor de solicitudes.
	 *
	 * @function
	 * @param {Error} error - El error producido durante la configuración de la solicitud.
	 * @returns {Promise<Error>} Una promesa rechazada con el error proporcionado.
	 */
	(error) => {
		return Promise.reject(error)
	}
)

/**
 * Exporta la instancia configurada de Axios para ser utilizada en la aplicación.
 *
 * @exports api
 */
export default api
