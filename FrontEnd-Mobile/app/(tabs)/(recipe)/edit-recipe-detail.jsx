import React, { useState, useEffect } from 'react'
import { View, Text, TextInput, TouchableOpacity, Alert, ScrollView } from 'react-native'
import { Picker } from '@react-native-picker/picker'
import { router, useLocalSearchParams } from 'expo-router'
import api from '../../../api/api'

const EditRecipeDetail = () => {
	const { id } = useLocalSearchParams() // Obtenemos el ID de la receta desde los parámetros
	const [recipe, setRecipe] = useState(null)
	const [loading, setLoading] = useState(true)
	const [error, setError] = useState(null)

	const categories = ['Vegan', 'Vegetarian', 'Meat', 'Fish', 'Pasta', 'Pizza', 'Salad', 'Dessert', 'Drinks', 'Breakfast', 'Soup']
	const numberOfPeople = [1, 2, 3, 4, 5, 6]

	const isValidUrl = (url) => {
		const urlRegex = /^(https?:\/\/)[^\s$.?#].[^\s]*$/
		return urlRegex.test(url)
	}

	// Cargar datos iniciales de la receta
	useEffect(() => {
		const fetchRecipe = async () => {
			try {
				const response = await api.get(`api/recipe/${id}`) 
				setRecipe(response.data)
			} catch (err) {
				console.error('Error fetching recipe:', err)
				setError('Unable to load the recipe.')
			} finally {
				setLoading(false)
			}
		}

		fetchRecipe()
	}, [id])

	const handleSaveChanges = async () => {
		if (!recipe.title || !recipe.description || !recipe.imageUrl || !recipe.cookingTime || !recipe.numPersons || !recipe.ingredients || !recipe.recipeCategory) {
			Alert.alert('Error', 'Please fill out all fields.')
			return
		}

		if (!isValidUrl(recipe.imageUrl)) {
			Alert.alert('Error', 'Please enter a valid URL for the image.')
			return
		}

		if (isNaN(recipe.cookingTime) || Number(recipe.cookingTime) <= 0) {
			Alert.alert('Error', 'Cooking Time must be a valid positive number.')
			return
		}

		try {
			const response = await api.put(`/api/recipe/${id}`, {
				title: recipe.title,
				imageUrl: recipe.imageUrl,
				description: recipe.description,
				cookingTime: parseInt(recipe.cookingTime),
				numPersons: parseInt(recipe.numPersons),
				ingredients: recipe.ingredients,
				recipeCategory: recipe.recipeCategory
			})

			if (response.status === 200) {
				Alert.alert('Success', 'Recipe updated successfully.')
				router.back() // Regresa a la pantalla anterior
			} else {
				Alert.alert('Error', 'Failed to update the recipe.')
			}
		} catch (err) {
			console.error('Error updating recipe:', err)
			Alert.alert('Error', 'An error occurred while updating the recipe.')
		}
	}

	if (loading) {
		return (
			<View className='flex-1 justify-center items-center'>
				<Text>Loading...</Text>
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
		<ScrollView className='flex-1 bg-green-900 p-5'>
			<Text className='text-3xl font-bold text-white mb-5 text-center'>Edit your Recipe</Text>

			{/* Recipe Title */}
			<Text className='text-lg font-bold text-white mb-2'>Recipe Title</Text>
			<TextInput className='bg-green-500 text-white p-3 rounded-md mb-4' placeholder='Title' placeholderTextColor='black' value={recipe.title} onChangeText={(text) => setRecipe({ ...recipe, title: text })} />

			{/* Recipe Description */}
			<Text className='text-lg font-bold text-white mb-2'>Recipe Description</Text>
			<TextInput className='bg-green-500 text-white p-3 rounded-md mb-4' placeholder='Description' placeholderTextColor='black' value={recipe.description} onChangeText={(text) => setRecipe({ ...recipe, description: text })} multiline />

			{/* Recipe Image */}
			<Text className='text-lg font-bold text-white mb-2'>Recipe Image</Text>
			<TextInput className='bg-green-500 text-white p-3 rounded-md mb-4' placeholder='https://example.com/image.jpg' placeholderTextColor='black' value={recipe.imageUrl} onChangeText={(text) => setRecipe({ ...recipe, imageUrl: text })} />

			{/* Cooking Time */}
			<Text className='text-lg font-bold text-white mb-2'>Cooking Time</Text>
			<TextInput
				className='bg-green-500 text-white p-3 rounded-md mb-4'
				placeholder='Cooking Time in minutes'
				placeholderTextColor='black'
				value={recipe.cookingTime.toString()}
				onChangeText={(text) => setRecipe({ ...recipe, cookingTime: parseInt(text) })}
				keyboardType='numeric'
			/>

			{/* Servings per Person */}
			<Text className='text-lg font-bold text-white mb-2'>Servings per person</Text>
			<View className='bg-green-500 rounded-md mb-4'>
				<Picker selectedValue={recipe.numPersons.toString()} onValueChange={(itemValue) => setRecipe({ ...recipe, numPersons: parseInt(itemValue) })}>
					<Picker.Item label='Select servings per person' value='' />
					{numberOfPeople.map((num) => (
						<Picker.Item key={num} label={num.toString()} value={num.toString()} />
					))}
				</Picker>
			</View>

			{/* Ingredients */}
			<Text className='text-lg font-bold text-white mb-2'>Ingredients Needed</Text>
			<TextInput className='bg-green-500 text-white p-3 rounded-md mb-4' placeholder='Ingredients' placeholderTextColor='black' value={recipe.ingredients} onChangeText={(text) => setRecipe({ ...recipe, ingredients: text })} multiline />

			{/* Recipe Category */}
			<Text className='text-lg font-bold text-white mb-2'>Recipe Category</Text>
			<View className='bg-green-500 rounded-md mb-4'>
				<Picker selectedValue={recipe.recipeCategory} onValueChange={(itemValue) => setRecipe({ ...recipe, recipeCategory: itemValue })}>
					<Picker.Item label='Select recipe category' value='' />
					{categories.map((cat) => (
						<Picker.Item key={cat} label={cat} value={cat} />
					))}
				</Picker>
			</View>

			{/* Save Button */}
			<TouchableOpacity className='bg-green-500 py-3 px-8 rounded-full mt-5 mb-20' onPress={handleSaveChanges}>
				<Text className='text-white text-lg font-bold text-center'>Save Changes</Text>
			</TouchableOpacity>
		</ScrollView>
	)
}

export default EditRecipeDetail
