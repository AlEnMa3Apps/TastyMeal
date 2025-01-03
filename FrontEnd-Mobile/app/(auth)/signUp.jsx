/**
 * @file SignUpForm.jsx
 * @description Componente de formulario de registro para la aplicación.
 * @autor Manuel García Nieto
 */
import { router } from 'expo-router'
import React, { useState } from 'react'
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from 'react-native'
import AsyncStorage from '@react-native-async-storage/async-storage'
import api from '../../api/api'

/**
 * @component
 * @name SignUpForm
 * @description
 * Este componente renderiza un formulario de registro que permite a los usuarios
 * crear una cuenta proporcionando su información personal. Maneja la validación de
 * los datos y la comunicación con la API para el registro de nuevos usuarios.
 *
 * @returns {JSX.Element} El formulario de registro.
 */
export default function SignUpForm() {
	const [username, setUsername] = useState('')
	const [email, setEmail] = useState('')
	const [firstName, setFirstName] = useState('')
	const [lastName, setLastName] = useState('')
	const [password, setPassword] = useState('')
	
/**
   * @function validateEmail
   * @description
   * Valida el formato de una dirección de correo electrónico.
   *
   * @param {string} email - La dirección de correo electrónico a validar.
   * @returns {boolean} `true` si el correo electrónico es válido, de lo contrario `false`.
   */
	const validateEmail = (email) => {
		const re = /\S+@\S+\.\S+/
		return re.test(email)
	}

 /**
   * @function handleSignUp
   * @description
   * Maneja el proceso de registro de un nuevo usuario. Valida los campos del formulario,
   * envía una solicitud a la API para registrar al usuario y maneja la respuesta.
   */
	const handleSignUp = async () => {
		if (!username || !email || !firstName || !lastName || !password) {
		  Alert.alert('Error', 'Please fill in all the fields.')
		  return
		}

		if (!validateEmail(email)) {
		  Alert.alert('Error', 'Please enter a valid email address.')
		  return
		}

		if (password.length < 4) {
		  Alert.alert('Error', 'Password must be at least 4 characters long.')
		  return
		}

		// send data to bakcend
		console.log('Username:', username)
		console.log('Email:', email)
		console.log('First Name:', firstName)
		console.log('Last Name:', lastName)
		console.log('Password:', password)
		// router.push('/(main)/home')
		try {
			const response = await api.post('/auth/register', {
				username,
				password,
				email,
				firstName,
				lastName
			})

			console.log(response.data)
			console.log(response.status)

			if (response.status === 200 || response.status === 201) {
				Alert.alert('Successful registration, Your account has been created. Please log in.')

				router.replace('/(auth)/login')
			} else {
				Alert.alert('Error', 'Sign Up failed. Please try again!')
			}
		} catch (error) {
			// Error handling
			if (error.response && error.response.data && error.response.data.error) {
				Alert.alert('Error', error.response.data.error)
			} else {
				Alert.alert('Error', 'Sing up failed. Please try again.')
			}
			console.error(' Error to register... ', error)
		}
	}

	return (
		<View style={styles.container}>
			<Text style={styles.title}>Let's Get Started</Text>
			<Text style={styles.subtitle}>Please fill in the details to create an account</Text>

			<TextInput style={styles.input} placeholder='Enter your username' value={username} onChangeText={setUsername} />
			<TextInput style={styles.input} placeholder='Enter your email' value={email} onChangeText={setEmail} keyboardType='email-address' autoCapitalize='none' />
			<TextInput style={styles.input} placeholder='Enter your first name' value={firstName} onChangeText={setFirstName} />
			<TextInput style={styles.input} placeholder='Enter your last name' value={lastName} onChangeText={setLastName} />
			<TextInput style={styles.input} placeholder='Enter your password' value={password} onChangeText={setPassword} secureTextEntry />

			<TouchableOpacity style={styles.button} onPress={handleSignUp}>
				<Text style={styles.buttonText}>Sign Up</Text>
			</TouchableOpacity>

			<Text style={styles.loginText}>
				Already have an account?{' '}
				<Text
					style={styles.loginLink}
					onPress={() => {
						router.push('/(auth)/login')
					}}>
					Login
				</Text>
			</Text>
		</View>
	)
}

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
		textAlign: 'center',
		marginBottom: 10
	},
	subtitle: {
		fontSize: 14,
		textAlign: 'center',
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
	button: {
		backgroundColor: '#28a745',
		borderRadius: 25,
		paddingVertical: 15,
		alignItems: 'center',
		marginBottom: 20,
		// Sombra para iOS
		shadowColor: '#000', // Color de la sombra
		shadowOffset: { width: 0, height: 4 },
		shadowOpacity: 0.3, // Opacidad de la sombra
		shadowRadius: 4.65, // Radio de la sombra
		// Elevación para Android
		elevation: 8
	},
	buttonText: {
		color: '#fff',
		fontSize: 18,
		fontWeight: 'bold'
	},
	loginText: {
		textAlign: 'center',
		color: '#7e7e7e'
	},
	loginLink: {
		color: '#28a745',
		fontWeight: 'bold'
	}
})
