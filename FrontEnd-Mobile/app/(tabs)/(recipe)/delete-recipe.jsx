import { View, Text, FlatList, ActivityIndicator, Image, TouchableOpacity, Alert } from 'react-native'
import React, { useState, useEffect } from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'
import api from '../../../api/api'
import FontAwesome6 from '@expo/vector-icons/FontAwesome6'
import MaterialIcons from '@expo/vector-icons/MaterialIcons'

const DeleteRecipe = () => {
	const [recipes, setRecipes] = useState([])
	const [loading, setLoading] = useState(true)
	const [error, setError] = useState(null)

	// Función para cargar recetas del usuario
	const fetchUserRecipes = async () => {
		setLoading(true)
		try {
			const response = await api.get('/api/recipes') // Endpoint para obtener recetas del usuario
			setRecipes(response.data)
		} catch (err) {
			setError('Unable to load your recipes.')
			console.error(err)
		} finally {
			setLoading(false)
		}
	}

	// Cargar recetas al montar el componente
	useEffect(() => {
		fetchUserRecipes()
	}, [])

	// Función para eliminar una receta
	const handleDeleteRecipe = async (id) => {
		Alert.alert(
			'Delete Recipe',
			'Are you sure you want to delete this recipe?',
			[
				{ text: 'Cancel', style: 'cancel' },
				{
					text: 'Yes',
					onPress: async () => {
						try {
							const response = await api.delete(`/api/recipe/${id}`) // Endpoint para eliminar la receta
							if (response.status === 200) {
								Alert.alert('Success', 'Recipe deleted successfully.')
								// Actualizar la lista de recetas después de eliminar
								setRecipes((prevRecipes) => prevRecipes.filter((recipe) => recipe.id !== id))
							} else {
								Alert.alert('Error', 'Failed to delete recipe.')
							}
						} catch (err) {
							console.error('Error deleting recipe:', err)
							Alert.alert('Error', 'An error occurred while deleting the recipe.')
						}
					}
				}
			],
			{ cancelable: true }
		)
	}

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
		<SafeAreaView className='flex-1 bg-slate-50'>
			<Text className='text-2xl font-bold text-center px-10 pb-4 pt-0'>Delete Recipe</Text>
			<FlatList
				data={recipes}
				keyExtractor={(item) => item.id.toString()}
				renderItem={({ item }) => (
					<View className='p-6 mx-6 my-2 border border-green-900 rounded-lg bg-stone-800'>
						<View className='flex-col items-center'>
							<Image source={{ uri: item.imageUrl }} className='w-full h-56 rounded-lg mb-4' />
						</View>
						<Text className='text-2xl font-bold text-slate-100'>{item.title}</Text>
						<Text className='text-base text-gray-300 my-2'>{item.description}</Text>

						<View className='flex-row my-2'>
							<View className='flex-row my-2 border border-slate-50 py-2 px-4 rounded-3xl'>
							<FontAwesome6 name='clock-four' size={20} color='lightgray' />
								<Text className='text-sm text-gray-300 pl-2'>{item.cookingTime} min</Text>
							</View>
							<View className='flex-row my-2 mx-4 border border-slate-50 py-2 px-4 rounded-3xl'>
							<MaterialIcons name='people' size={20} color='lightgray' />
								<Text className='text-sm text-gray-300 pl-2'>{item.numPersons}</Text>
							</View>
							<View className='w-28 my-2 rounded-3xl bg-green-600 justify-center items-center'>
								<Text className='text-sm text-gray-100 text-center font-bold'>{item.categoryName}</Text>
							</View>
						</View>

						{/* Botón para eliminar */}
						<TouchableOpacity className='bg-red-500 py-3 px-8 rounded-full mt-5' onPress={() => handleDeleteRecipe(item.id)}>
							<Text className='text-white text-xl font-bold text-center'>Delete</Text>
						</TouchableOpacity>
					</View>
				)}
			/>
		</SafeAreaView>
	)
}

export default DeleteRecipe
