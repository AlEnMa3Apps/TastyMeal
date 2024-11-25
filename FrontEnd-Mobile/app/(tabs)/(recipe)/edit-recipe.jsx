import { View, Text, FlatList, ActivityIndicator, Image, TouchableOpacity } from 'react-native'
import React, { useCallback, useState } from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'
import api from '../../../api/api'
import { useFocusEffect } from '@react-navigation/native'
import { router } from 'expo-router'
import FontAwesome6 from '@expo/vector-icons/FontAwesome6'
import MaterialIcons from '@expo/vector-icons/MaterialIcons'

const EditRecipe = () => {
	const [recipes, setRecipes] = useState([])
	const [loading, setLoading] = useState(true)
	const [error, setError] = useState(null)

	const fetchUserRecipes = async () => {
		try {
			const response = await api.get('/api/recipes')
			setRecipes(response.data)
		} catch (err) {
			setError('Unable to load your recipes.')
			console.error(err)
		} finally {
			setLoading(false)
		}
	}

	useFocusEffect(
		useCallback(() => {
			fetchUserRecipes()
		}, [])
	)

	if (loading) {
		return (
			<View className='flex-1 justify-center items-center'>
				<ActivityIndicator size='large' color='#0000ff' />
			</View>
		)
	}

	if (error) {
		return (
			<View className='flex-1 justify-center items-center'>
				<Text className='text-red-500 text-lg'>{error}</Text>
			</View>
		)
	}

	return (
		<SafeAreaView className='flex-1 bg-slate-100'>
			<Text className='text-2xl font-bold text-center p-4'>Select one of your recipes to edit</Text>
			<FlatList
				data={recipes}
				keyExtractor={(item) => item.id.toString()}
				renderItem={({ item }) => (
					<TouchableOpacity
						onPress={() =>
							router.push({
								pathname: '/edit-recipe-detail',
								params: { id: item.id }
							})
						}>
						<View className='p-6 mx-6 my-2 border border-green-900 rounded-lg bg-stone-800'>
							<View className='flex-col items-center'>
								<Image source={{ uri: item.imageUrl }} className='w-full h-56 rounded-lg mb-4' />
							</View>
							<Text className='text-2xl font-bold text-slate-100'>{item.title}</Text>
							<Text className='text-base text-gray-300 my-2'>{item.description}</Text>

							<View className='flex-row my-2'>
								<View className='flex-row my-2 border border-slate-50 py-2 px-4 rounded-3xl'>
									<FontAwesome6 name='clock-four' size={24} color='lightgray' />
									<Text className='text-sm text-gray-300 pl-2'>{item.cookingTime} min</Text>
								</View>
								<View className='flex-row my-2 mx-4 border border-slate-50 py-2 px-4 rounded-3xl'>
									<MaterialIcons name='people' size={24} color='lightgray' />
									<Text className='text-sm text-gray-300 pl-2'>{item.numPersons}</Text>
								</View>
								<View className='w-28 my-2 rounded-3xl bg-green-800 justify-center items-center'>
									<Text className='text-sm text-gray-100 text-center font-bold'>{item.categoryName}</Text>
								</View>
							</View>
						</View>
					</TouchableOpacity>
				)}
				ItemSeparatorComponent={() => <View className='h-4' />}
			/>
		</SafeAreaView>
	)
}

export default EditRecipe
