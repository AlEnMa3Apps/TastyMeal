import { View, Text, Image, Alert, TouchableOpacity } from 'react-native'
import React from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'
import { router } from 'expo-router'
import api from '../../../api/api'

const profile = () => {
	const handleEditProfile = () => {
		// Navegar a la pantalla de edición de perfil
	}

	const handleDeleteProfile = () => {
		// Confirmación de eliminación de perfil
		Alert.alert('Delete Profile', 'Are you sure you want to delete your profile?', [
			{ text: 'Cancel', style: 'cancel' },
			{
				text: 'Yes',
				onPress: () => {
					// Lógica para eliminar el perfil
					console.log('Profile deleted')
				},
				style: 'destructive'
			}
		])
	}

	const handleResetPassword = () => {
		// Lógica para resetear contraseña
		console.log('Password reset')
	}

	const handleLogout = () => {
		// Lógica para cerrar sesión
		console.log('Logged out')
	}
	return (
		<SafeAreaView>
			<View className='h-full'>
				<View className='items-center py-14'>
					<Image source={{ uri: 'https://thispersondoesnotexist.com/' }} className='w-52 h-52 rounded-full mb-4 border-black border-2' />
					<Text className='text-3xl font-bold text-gray-800'>John Doe</Text>
					<Text className='text-2xl text-gray-800'>johndoe@example.com</Text>
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

export default profile
