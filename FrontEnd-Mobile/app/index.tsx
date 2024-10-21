import React, { useEffect } from 'react'
import { View, Text, Image, Button, StyleSheet, TouchableOpacity, BackHandler } from 'react-native'
import { Link, useRouter } from 'expo-router'

export default function WelcomeScreen() {
	const router = useRouter()

	useEffect(() => {
		const backAction = () => {
			return true
		}

		const backHandler = BackHandler.addEventListener('hardwareBackPress', backAction)

		return () => backHandler.remove()
	}, [])

	const handleRegister = () => {
		router.push('/(auth)/signUp')
	}

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
		// Sombra para iOS
		shadowColor: '#000', // Color de la sombra
		shadowOffset: { width: 0, height: 4 }, // Desplazamiento de la sombra
		shadowOpacity: 0.3, // Opacidad de la sombra
		shadowRadius: 4.65, // Radio de la sombra
		// Sombra para Android
		elevation: 8 // Nivel de elevación para Android
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
