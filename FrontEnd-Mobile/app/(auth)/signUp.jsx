import { router } from 'expo-router'
import React, { useState } from 'react'
import { View, Text, TextInput, TouchableOpacity, StyleSheet } from 'react-native'

export default function SignUpForm() {
	const [name, setName] = useState('')
	const [email, setEmail] = useState('')
	const [password, setPassword] = useState('')

	const handleSignUp = () => {
		console.log('Name:', name)
		console.log('Email:', email)
		console.log('Password:', password)
		router.push('/(main)/home')
	}

	return (
		<View style={styles.container}>
			<Text style={styles.title}>Let's Get Started</Text>
			<Text style={styles.subtitle}>Please fill the details to create an account</Text>

			<TextInput style={styles.input} placeholder='Enter your name' value={name} onChangeText={setName} />
			<TextInput style={styles.input} placeholder='Enter your email' value={email} onChangeText={setEmail} keyboardType='email-address' />
			<TextInput style={styles.input} placeholder='Enter your password' value={password} onChangeText={setPassword} secureTextEntry />

			<TouchableOpacity style={styles.button} onPress={handleSignUp}>
				<Text style={styles.buttonText}>Sign Up</Text>
			</TouchableOpacity>

			<Text style={styles.loginText}>
				Already have an account?{' '}
				<Text
					style={styles.loginLink}
					onPress={() => {
						console.log('Navigate to Login')
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
		//iOS
		shadowColor: '#000', // Color de la sombra
		shadowOffset: { width: 0, height: 4 },
		shadowOpacity: 0.3, // Opacidad de la sombra
		shadowRadius: 4.65, // Radio de la sombra
		//Android
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
