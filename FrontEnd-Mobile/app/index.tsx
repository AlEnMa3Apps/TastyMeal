/**
 * @file WelcomeScreen.jsx
 * @description Componente de pantalla de bienvenida que presenta la aplicación y permite navegar al registro o inicio de sesión.
 * @autor Manuel García Nieto
 */
import React, { useEffect } from 'react'
import { View, Text, Image, Button, StyleSheet, TouchableOpacity, BackHandler } from 'react-native'
import { useRouter } from 'expo-router'

/**
 * @component
 * @name WelcomeScreen
 * @description
 * Este componente muestra la pantalla de bienvenida de la aplicación, incluyendo una imagen principal,
 * textos descriptivos y botones para iniciar el registro o el inicio de sesión. También deshabilita el
 * botón de retroceso en dispositivos Android para evitar que el usuario salga de la aplicación desde esta pantalla.
 *
 * @returns {JSX.Element} La interfaz de usuario de la pantalla de bienvenida.
 */
export default function WelcomeScreen() {
	const router = useRouter()

	useEffect(() => {
		const backAction = () => {
			return true
		}

		const backHandler = BackHandler.addEventListener('hardwareBackPress', backAction)

		return () => backHandler.remove()
	}, [])

	/**
	 * @function handleRegister
	 * @description
	 * Maneja la navegación hacia la pantalla de registro cuando el usuario presiona el botón "Getting Started".
	 */
	const handleRegister = () => {
		router.push('/(auth)/signUp')
	}

	/**
	 * @function handleLogin
	 * @description
	 * Maneja la navegación hacia la pantalla de inicio de sesión cuando el usuario presiona el texto "Login".
	 */
	const handleLogin = () => {
		router.push('/(auth)/login')
	}

	return (
		<View className='flex-1 p-5 items-center justify-between bg-lime-500'>
			<Image className='w-full h-1/2 mt-5 rounded-3xl  ' source={require('../assets/images/main-picture.png')} resizeMode='cover' />

			<Text className='text-3xl text-center font-black text-slate-700'>Welcome to Tasty Meal!</Text>

			<Text className='text-lg mb-4 text-center text-slate-700'> Discover a world of culinary delights with us!</Text>

			<TouchableOpacity style={styles.button} onPress={handleRegister}>
				<Text style={styles.buttonText}>Getting Started</Text>
			</TouchableOpacity>

			<TouchableOpacity onPress={handleLogin}>
				<Text className='text-slate-800 text-center mb-12 text-lg'>
					Already have an account? <Text className='font-bold'> Login</Text>
				</Text>
			</TouchableOpacity>
		</View>
	)
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		padding: 16,
		alignItems: 'center',
		justifyContent: 'space-between'
	},
	image: {
		width: '100%',
		height: '50%',
		marginTop: 50
	},
	button: {
		width: '80%',
		backgroundColor: '#000000',
		paddingVertical: 15,
		borderRadius: 30,
		marginBottom: 20,
		alignItems: 'center',
		shadowColor: '#000',
		shadowOffset: { width: 0, height: 4 }, // Desplazamiento de la sombra
		shadowOpacity: 0.3, // Opacidad de la sombra
		shadowRadius: 4.65, // Radio de la sombra
		elevation: 8
	},
	buttonText: {
		color: '#FFFFFF',
		fontSize: 20,
		fontWeight: '600'
	},
	loginText: {
		color: '#007AFF',
		textAlign: 'center',
		marginBottom: 40,
		textDecorationLine: 'underline'
	}
})
