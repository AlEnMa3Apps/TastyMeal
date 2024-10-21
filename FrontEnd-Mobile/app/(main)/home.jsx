// app/main/home.jsx
import React from 'react'
import { View, Text, FlatList, Image, TouchableOpacity } from 'react-native'
import { useRouter } from 'expo-router'
// import { useAuth } from '../../context/AuthContext'

// const recipes = [
// 	{
// 		id: '1',
// 		title: 'Ensalada César',
// 		image: require('../../assets/cesar-salad.jpg')
// 	},
// 	{
// 		id: '2',
// 		title: 'Paella Valenciana',
// 		image: require('../../assets/paella.jpg')
// 	},
// 	{
// 		id: '3',
// 		title: 'Tacos al Pastor',
// 		image: require('../../assets/tacos.jpg')
// 	}
// ]

export default function HomeScreen() {
	const router = useRouter()
	// const { logout } = useAuth()

	const handleLogout = () => {
		// logout() // Limpia el estado de autenticación
		router.replace('/') // Redirige al usuario a la pantalla de login
	}

	const renderItem = ({ item }) => (
		<View className='mb-5 rounded-lg overflow-hidden bg-gray-200'>
			<Image source={item.image} className='w-full h-48' resizeMode='cover' />
			<Text className='text-xl p-4'>{item.title}</Text>
		</View>
	)

	return (
		<View className='flex-1 bg-green-500 items-center justify-center'>
			<View className='pt-10 px-4 flex-row justify-between items-center'>
				<Text className='text-4xl text-white font-bold'>Tasty Meal Recipes</Text>
				<TouchableOpacity onPress={handleLogout} className='py-2 px-3 mt-52 bg-red-600 rounded-lg'>
					<Text className='text-white font-semibold'>Log Out</Text>
				</TouchableOpacity>
			</View>
			{/* <FlatList data={recipes} renderItem={renderItem} keyExtractor={(item) => item.id} contentContainerStyle={{ paddingHorizontal: 16, paddingTop: 20 }} /> */}
		</View>
	)
}
