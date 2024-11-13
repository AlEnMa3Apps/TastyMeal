import React, { useState, useEffect } from 'react'
import { View, Text, TextInput, TouchableOpacity, Alert } from 'react-native'
import api from '../../../api/api'
import { useFocusEffect } from '@react-navigation/native'
import { router } from 'expo-router'

const EditProfile = () => {
	const [username, setUsername] = useState('')
	const [email, setEmail] = useState('')
	const [firstName, setFirstName] = useState('')
	const [lastName, setLastName] = useState('')

	useFocusEffect(
		React.useCallback(() => {
			// Función para cargar los datos actuales del usuario
			const loadUserData = async () => {
				try {
					const response = await api.get('/api/user')
					const userData = response.data

					setUsername(userData.username)
					setEmail(userData.email)
					setFirstName(userData.firstName)
					setLastName(userData.lastName)
				} catch (error) {
					console.error('Error loading user data:', error)
					Alert.alert('Error', 'Could not load user data.')
				}
			}

			loadUserData()

			// Limpieza opcional si la necesitas
			return () => {
				setUsername('')
				setEmail('')
				setFirstName('')
				setLastName('')
			}
		}, [])
	)

	const handleSaveChanges = async () => {
		try {
			const response = await api.put('/api/user', {
				username,
				email,
				firstName,
				lastName
			})

			if (response.status === 200) {
				Alert.alert('Success', 'Profile updated successfully.')
				router.back()
			} else {
				Alert.alert('Error', 'Failed to update profile. Please try again.')
			}
		} catch (error) {
			console.error('Error updating profile:', error)
			console.log('Error updating profile:', error.status)
			console.error('Error updating profile:', error.code)
			Alert.alert('Error', 'An error occurred while updating the profile. Please try again later.')
		}
	}

	return (
		<View className='flex-1 bg-gray-100 p-6'>
			<Text className='text-2xl font-bold text-gray-800 mb-6 text-center mt-20'>Edit your profile</Text>

		
			<Text className='text-gray-700 mb-1'>First Name</Text>
			<TextInput className='bg-white p-4 rounded-lg shadow mb-4 text-xl' placeholder='First Name' value={firstName} onChangeText={setFirstName} />
			<Text className='text-gray-700 mb-1'>Last Name</Text>
			<TextInput className='bg-white p-4 rounded-lg shadow mb-4 text-xl' placeholder='Last Name' value={lastName} onChangeText={setLastName} />
			<Text className='text-gray-700 mb-1'>Email</Text>
			<TextInput className='bg-white p-4 rounded-lg shadow mb-16 text-xl' placeholder='Email' value={email} onChangeText={setEmail} keyboardType='email-address' autoCapitalize='none' />

			<TouchableOpacity className='bg-green-500 p-4 rounded-full shadow items-center' onPress={handleSaveChanges}>
				<Text className='text-white text-xl font-semibold'>Save Changes</Text>
			</TouchableOpacity>
		</View>
	)
}

export default EditProfile
