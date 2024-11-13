import { View, Text, Image, Alert, TouchableOpacity } from 'react-native'
import React, { useCallback, useState } from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'
import { router, Redirect } from 'expo-router'
import api from '../../../api/api'
import { useAuth } from '../../../context/AuthContext'
import { useFocusEffect } from '@react-navigation/native'

const Profile = () => {
	const { logout } = useAuth()
	const [user, setUser] = useState(null)

	useFocusEffect(
		useCallback(() => {
			// Función para obtener la información del usuario
			const fetchUserData = async () => {
				try {
					const response = await api.get('/api/user')
					if (response.data === 'INVALID TOKEN') {
						Alert.alert('Session Expired', 'Please log in again.')
						router.push('/(auth)/login')
					} else if (response.data === 'USER NOT FOUND') {
						Alert.alert('Error', 'User not found.')
					} else {
						setUser(response.data)
					}
				} catch (error) {
					console.error('Error fetching user data:', error)
					Alert.alert('Error', 'Could not fetch user data.')
				}
			}

			fetchUserData()

			return () => {
				setUser(null)
			}
		}, [])
	)

	console.log(user)

	const handleEditProfile = () => {
		// Navegar a la pantalla de edición de perfil
		router.push('/(profile)/edit-profile')
	}

	const handleDeleteProfile = () => {
		// Confirmación de eliminación de perfil
		Alert.alert('Delete Profile', 'Are you sure you want to delete your profile?', [
			{ text: 'Cancel', style: 'cancel' },
			{
				text: 'Yes',
				onPress: async () => {
					try {
						// Realiza la solicitud DELETE al endpoint /user
						const response = await api.delete('/api/user')

						if (response.status === 200) {
							Alert.alert('Success', 'Profile has been deleted successfully.')

							// Llama a logout para limpiar el token y el rol del AsyncStorage y el estado
							await logout()

							// Redirige al usuario a la pantalla de login
							router.replace('/(auth)/login')
						} else {
							Alert.alert('Error', 'Failed to delete profile. Please try again.')
						}
					} catch (error) {
						console.error('Error deleting profile:', error)
						Alert.alert('Error', 'An error occurred while deleting the profile. Please try again later.')
					}
				}
			}
		])
	}

	const handleResetPassword = () => {
		// Lógica para resetear contraseña
		router.push('/(profile)/reset-password')
		console.log('Password reset')
	}

	const handleLogout = async () => {
		// Lógica para cerrar sesión
		await logout()
		router.replace('/(auth)/login')
		console.log('Logged out')
	}
	return (
		<SafeAreaView>
			<View className='h-full'>
				<View className='items-center py-14'>
					<Image source={{ uri: 'https://thispersondoesnotexist.com/' }} className='w-52 h-52 rounded-full mb-4 border-black border-2' />
					<Text className='text-3xl font-bold text-gray-800'>  {user ? `${user.firstName} ${user.lastName}` : 'Loading...'}</Text>
					<Text className='text-2xl text-gray-800 pl-3'>{user?.email || ''}</Text>
				</View>
				<View className='px-5 space-y-3 mb-10'>
					<TouchableOpacity className='bg-green-500 py-4 px-5 rounded-full shadow-2xl mb-4' onPress={handleEditProfile}>
						<Text className='text-2xl text-black font-bold text-center'>Edit Profile</Text>
					</TouchableOpacity>
					<TouchableOpacity className='bg-green-500 py-4 px-5 rounded-full shadow-2xl mb-4' onPress={handleDeleteProfile}>
						<Text className='text-2xl text-gray-800 font-bold text-center'>Delete Profile</Text>
					</TouchableOpacity>
					<TouchableOpacity className='bg-green-500 py-4 px-5 rounded-full shadow-2xl mb-4' onPress={handleResetPassword}>
						<Text className='text-2xl text-gray-800 font-bold text-center'>Reset password</Text>
					</TouchableOpacity>
					<TouchableOpacity className='bg-green-500 py-4 px-5 rounded-full shadow-2xl' onPress={handleLogout}>
						<Text className='text-2xl text-gray-800 font-bold text-center'>Log out</Text>
					</TouchableOpacity>
				</View>
			</View>
		</SafeAreaView>
	)
}

export default Profile
