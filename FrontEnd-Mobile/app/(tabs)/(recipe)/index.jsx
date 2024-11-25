import { View, Text, TouchableOpacity, ImageBackground } from 'react-native'
import React from 'react'
import { Alert } from 'react-native'
import { router } from 'expo-router'

const RecipeScreen = () => {
	const handleCreateRecipe = () => {
		// Navegar a la pantalla de creación de receta
		router.push('/(recipe)/create-recipe')
	}

	const handleEditRecipe = () => {
		// Navegar a la pantalla de edición de receta
		router.push('/(recipe)/edit-recipe')
	}

	const handleDeleteRecipe = () => {
		// Confirmación de eliminación de receta
		Alert.alert('Delete Recipe', 'Are you sure you want to delete this recipe?', [
			{ text: 'Cancel', style: 'cancel' },
			{
				text: 'Yes',
				onPress: async () => {
					try {
						// Realiza la solicitud DELETE al endpoint /recipe
						const response = await api.delete('/api/recipe')

						if (response.status === 200) {
							Alert.alert('Success', 'Recipe has been deleted successfully.')
						} else {
							Alert.alert('Error', 'Could not delete recipe.')
						}
					} catch (error) {
						console.error('Error deleting recipe:', error)
						Alert.alert('Error', 'Could not delete recipe.')
					}
				}
			}
		])
	}

	return (
		<ImageBackground source={{ uri: 'https://images.unsplash.com/photo-1476718406336-bb5a9690ee2a?q=80&w=2000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D' }} style={{ flex: 1 }} resizeMode='center'>
			<View className='h-full mt-80'>
				<View className='px-5 space-y-3 mb-10'>
					<TouchableOpacity className='bg-stone-800 py-4 px-5 rounded-full shadow-2xl mb-4 border-black border-2' onPress={handleCreateRecipe}>
						<Text className='text-2xl text-green-50 font-bold text-center'>Create Recipe</Text>
					</TouchableOpacity>

					<TouchableOpacity className='bg-stone-800 py-4 px-5 rounded-full shadow-2xl mb-4' onPress={handleEditRecipe}>
						<Text className='text-2xl text-green-50 font-bold text-center'>Edit Recipe</Text>
					</TouchableOpacity>

					<TouchableOpacity className='bg-stone-800 py-4 px-5 rounded-full shadow-2xl mb-4' onPress={handleDeleteRecipe}>
						<Text className='text-2xl text-green-50 font-bold text-center'>Delete Recipe</Text>
					</TouchableOpacity>
				</View>
			</View>
		</ImageBackground>
	)
}

export default RecipeScreen
