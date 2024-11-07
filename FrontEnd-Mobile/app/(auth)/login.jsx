/**
 * @file LoginForm.jsx
 * @description Componente de formulario de inicio de sesión para la aplicación.
 * @autor Manuel García Nieto
 */
import React, { useState } from 'react'
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from 'react-native'
import { Link, router } from 'expo-router'
import AsyncStorage from '@react-native-async-storage/async-storage'
import { useAuth } from '../../context/AuthContext'
import api from '../../api/api'

/**
 * @component
 * @name LoginForm
 * @description
 * Este componente renderiza un formulario de inicio de sesión que permite a los usuarios
 * ingresar su nombre de usuario y contraseña. Maneja la autenticación enviando una solicitud
 * a la API y gestionando la navegación según el rol del usuario.
 *
 * @returns {JSX.Element} El formulario de inicio de sesión.
 */
export default function LoginForm() {
	/**
	 * @hook
	 * @name useAuth
	 * @description
	 * Hook personalizado para acceder al contexto de autenticación.
	 *
	 * @typedef {Object} AuthContext
	 * @property {Function} login Función para actualizar el estado de autenticación.
	 */
	const { login } = useAuth()
	/**
	 * @hook
	 * @name useState
	 * @description
	 * Hooks para manejar el estado local del nombre de usuario y la contraseña.
	 *
	 * @typedef {Array} useStateReturn
	 * @property {string} 0 Valor actual del estado.
	 * @property {Function} 1 Función para actualizar el estado.
	 */
	const [username, setUsername] = useState('')
	const [password, setPassword] = useState('')
	/**
	 * @function
	 * @name handleLogin
	 * @description
	 * Maneja el proceso de inicio de sesión. Valida los campos, envía una solicitud a la API,
	 * almacena el token y el rol en AsyncStorage, actualiza el contexto de autenticación y
	 * navega a la pantalla correspondiente según el rol del usuario.
	 */
	const handleLogin = async () => {
		if (!username || !password) {
			Alert.alert('Error', 'Please fill in all the fields.')
			return
		}

		if (password.length < 4) {
			Alert.alert('Error', 'Password must be at least 4 characters long.')
			return
		}

		try {
			const response = await api.post('/auth/login', {
				username,
				password
			})

			// Manejar la respuesta
			const { token, role } = response.data
			console.log(response.data)
			console.log('Username:', username)
			console.log('Password:', password)

			// Almacenar el token y el rol
			await AsyncStorage.setItem('token', token)
			await AsyncStorage.setItem('role', role)

			// Actualizar el contexto de autenticación
			login(token, role)

			// Navegar según el rol
			 if (role === 'USER_VIP') {
				router.replace('/(tabs)/home')
			} else {
			 	router.replace('/(tabs)/home')
			 }
		} catch (error) {
			// Verifica si es un error de red (backend desconectado)
			if (error.code === 'ECONNREFUSED') {
				Alert.alert('Error', 'Network error. Please check your connection.');
			} else if (error.response && error.response.data && error.response.data.error) {
				// Error de autenticación o cualquier error específico enviado desde el backend
				Alert.alert('Error', error.response.data.error);
			} else {
				// Muestra un mensaje de 
				Alert.alert('Error', 'Wrong credentials.');
			}
			console.error('Error logging in:', error);
		}
	}

	const handleForgotPassword = () => {
		// Navegar a la pantalla de restablecimiento de contraseña
		router.push('/(tabs)/(profile)/reset-password')
	}

	return (
		<View style={styles.container}>
			<Text style={styles.title}>Hey,</Text>
			<Text style={styles.title}>Welcome Back</Text>
			<Text style={styles.subtitle}>Please login to continue</Text>

			<TextInput style={styles.input} placeholder='Enter your username' value={username} onChangeText={setUsername} testID='username-input' />
			<TextInput style={styles.input} placeholder='Enter your password' value={password} onChangeText={setPassword} secureTextEntry testID='password-input' />

			<View style={styles.forgotPasswordContainer}>
				<TouchableOpacity onPress={handleForgotPassword}>
					<Text style={styles.forgotPasswordText}>Forgot Password?</Text>
				</TouchableOpacity>
			</View>

			<TouchableOpacity style={styles.button} onPress={handleLogin} testID='login-button'>
				<Text style={styles.buttonText}>Login</Text>
			</TouchableOpacity>

			<Text style={styles.signupText}>
				Don't have an account?{' '}
				<Link href='signUp' style={styles.signupLink}>
					Sign Up
				</Link>
			</Text>
		</View>
	)
}

/**
 * @constant
 * @name styles
 * @description
 * Define los estilos utilizados en el componente LoginForm.
 */
const styles = StyleSheet.create({
	container: {
		flex: 1,
		justifyContent: 'center',
		paddingHorizontal: 20,
		backgroundColor: '#fff'
	},
	title: {
		fontSize: 28,
		fontWeight: 'bold',
		textAlign: 'left'
	},
	subtitle: {
		fontSize: 14,
		textAlign: 'left',
		color: '#7e7e7e',
		marginBottom: 20
	},
	input: {
		borderWidth: 1,
		borderColor: '#ccc',
		borderRadius: 25,
		paddingVertical: 10,
		paddingHorizontal: 15,
		marginBottom: 15
	},
	forgotPasswordContainer: {
		alignItems: 'flex-end',
		marginBottom: 20
	},
	forgotPasswordText: {
		color: '#7e7e7e',
		fontWeight: 'bold'
	},
	button: {
		backgroundColor: '#28a745',
		borderRadius: 25,
		paddingVertical: 15,
		alignItems: 'center',
		marginBottom: 20,
		shadowColor: '#000',
		shadowOffset: { width: 0, height: 4 },
		shadowOpacity: 0.3,
		shadowRadius: 4.65,
		elevation: 8
	},
	buttonText: {
		color: '#fff',
		fontSize: 18,
		fontWeight: 'bold'
	},
	signupText: {
		textAlign: 'center',
		color: '#7e7e7e'
	},
	signupLink: {
		color: '#28a745',
		fontWeight: 'bold'
	}
})
