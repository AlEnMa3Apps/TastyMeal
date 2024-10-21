import axios from 'axios'
import AsyncStorage from '@react-native-async-storage/async-storage'
import { Platform } from 'react-native'

const baseURL =
	Platform.OS === 'android'
		? 'http://10.0.2.2:8080' // Android
		: 'http://localhost:8080' // iOS

const api = axios.create({
	baseURL: baseURL
})

api.interceptors.request.use(
	async (config) => {
		const token = await AsyncStorage.getItem('token')
		if (token) {
			config.headers.Authorization = `Bearer ${token}`
		}
		return config
	},
	(error) => {
		return Promise.reject(error)
	}
)

export default api
