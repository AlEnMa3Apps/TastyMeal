import React, { useState } from 'react'
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from 'react-native'
import { Link, router } from 'expo-router'

export default function LoginForm() {
	const [username, setUsername] = useState('')
	const [password, setPassword] = useState('')

	const handleLogin = () => {
		// if (!username || !password) {
		// 	Alert.alert('Error', 'Please fill in all the fields.')
		// 	return
		// }
		console.log('Username:', username)
		console.log('Password:', password)
		router.push('/(main)/home')
	}

	return (
		<View style={styles.container}>
			<Text style={styles.title}>Hey,</Text>
			<Text style={styles.title}>Welcome Back</Text>
			<Text style={styles.subtitle}>Please login to continue</Text>

			<TextInput style={styles.input} placeholder='Enter your username' value={username} onChangeText={setUsername} keyboardType='email-address' />
			<TextInput style={styles.input} placeholder='Enter your password' value={password} onChangeText={setPassword} secureTextEntry />

			<View style={styles.forgotPasswordContainer}>
				<TouchableOpacity>
					<Text style={styles.forgotPasswordText}>Forgot Password?</Text>
				</TouchableOpacity>
			</View>

			<TouchableOpacity style={styles.button} onPress={handleLogin}>
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
