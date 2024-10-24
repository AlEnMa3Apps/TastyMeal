import React from 'react'
import { View, Text, FlatList, Image, TouchableOpacity } from 'react-native'
import { useRouter } from 'expo-router'
import { useAuth } from '../../context/AuthContext'

const recipes = [
	{
		id: '1',
		title: 'Ensalada César',
		image: require('../../assets/images/cesar-salad.png')
	},
	{
		id: '2',
		title: 'Paella Valenciana',
		image: require('../../assets/images/paella.png')
	},
	{
		id: '3',
		title: 'Tacos al Pastor',
		image: require('../../assets/images/tacos.png')
	}
]

export default function HomeScreenVIP() {
	const router = useRouter()
	const { logout } = useAuth()

	const handleLogout = () => {
		logout()
		router.replace('/')
	}

	const renderItem = ({ item }) => (
		<View className='mb-5 rounded-lg overflow-hidden bg-gray-200'>
			<Image source={item.image} className='w-full h-48' resizeMode='cover' />
			<Text className='text-xl p-4'>{item.title}</Text>
		</View>
	)

	return (
		<View className='flex-1 bg-green-500 items-center justify-center p-5'>
			<View className='pt-10 px-4 flex-col justify-center items-center mt-20'>
				<Text className='text-4xl text-white font-bold mb-5'>Tasty Meal Recipes</Text>
				<Text className='text-4xl text-black font-bold'>VIP User</Text>
				<TouchableOpacity onPress={handleLogout} className='py-2 px-3 mt-5 bg-red-600 rounded-lg'>
					<Text className='text-white font-semibold text-lg shadow-lg shadow-black'>Log Out</Text>
				</TouchableOpacity>
			</View>
			<FlatList data={recipes} renderItem={renderItem} keyExtractor={(item) => item.id} contentContainerStyle={{ paddingHorizontal: 16, paddingTop: 20 }} />
		</View>
	)
}
