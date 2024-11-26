/**
 * Componente `CreateRecipe`.
 * Permite al usuario crear una nueva receta mediante un formulario con validaciones.
 *
 * @returns {JSX.Element} Vista del formulario para crear una nueva receta.
 * @autor Manuel García Nieto
 */
// Importación de módulos necesarios para la creación del componente.
import React, { useState } from 'react'
import { View, Text, TextInput, TouchableOpacity, Alert, ScrollView } from 'react-native'
import { Picker } from '@react-native-picker/picker'
import { router } from 'expo-router'
import api from '../../../api/api'

const CreateRecipe = () => {
	const [title, setTitle] = useState('')
	const [description, setDescription] = useState('')
	const [image, setImage] = useState('')
	const [cookingTime, setCookingTime] = useState('')
	const [servings, setServings] = useState('')
	const [ingredients, setIngredients] = useState('')
	const [category, setCategory] = useState('')

	const categories = ['Vegan', 'Vegetarian', 'Meat', 'Fish', 'Pasta', 'Pizza', 'Salad', 'Dessert', 'Drinks', 'Breakfast', 'Soup']
	const numberOfPeople = [1, 2, 3, 4, 5, 6]

	/**
	 * Valida si una URL es válida.
	 * @param {string} url - URL a validar.
	 * @returns {boolean} true si la URL es válida, false en caso contrario.
	 */
	const isValidUrl = (url) => {
		const urlRegex = /^(https?:\/\/)[^\s$.?#].[^\s]*$/
		return urlRegex.test(url)
	}

	/**
	 * Maneja la acción de crear una nueva receta.
	 * Valida los datos ingresados y envía la solicitud al backend.
	 */
	const handleCreateRecipe = async () => {
		if (!title || !description || !image || !cookingTime || !servings || !ingredients || !category) {
			Alert.alert('Error', 'Please fill out all fields')
			return
		}

		if (!isValidUrl(image)) {
			Alert.alert('Error', 'Please enter a valid URL for the image')
			return
		}

		if (isNaN(cookingTime) || Number(cookingTime) <= 0) {
			Alert.alert('Error', 'Cooking Time must be a valid positive number')
			return
		}

		const recipeData = {
			title,
			imageUrl: image,
			description,
			cookingTime: parseInt(cookingTime),
			numPersons: parseInt(servings),
			ingredients,
			recipeCategory: category
		}

		try {
			const response = await api.post('/api/recipe', recipeData)

			if (response.status === 201 || response.status === 200) {
				Alert.alert('Success', 'Recipe created successfully')
				router.back()
			} else {
				Alert.alert('Error', 'Failed to create recipe')
			}
		} catch (error) {
			console.error('Error creating recipe:', error)
			Alert.alert('Error', 'An error occurred while creating the recipe')
		}
	}

	//Renderiza la vista con el formulario para crear una nueva receta.
	return (
		<ScrollView className='flex-1 bg-green-900 p-5'>
			<Text className='text-3xl font-bold text-white mb-5 text-center'>New Recipe</Text>

			{/* Recipe Title */}
			<Text className='text-lg font-bold text-white mb-2'>Recipe Title</Text>
			<TextInput className='bg-green-500 text-white p-3 rounded-md mb-4' placeholder='Title' placeholderTextColor='black' value={title} onChangeText={setTitle} />

			{/* Recipe Description */}
			<Text className='text-lg font-bold text-white mb-2'>Recipe Description</Text>
			<TextInput className='bg-green-500 text-white p-3 rounded-md mb-4' placeholder='Description' placeholderTextColor='black' value={description} onChangeText={setDescription} multiline />

			{/* Recipe Image */}
			<Text className='text-lg font-bold text-white mb-2'>Recipe Image</Text>
			<TextInput className='bg-green-500 text-white p-3 rounded-md mb-4' placeholder='https://example.com/image.jpg' placeholderTextColor='black' value={image} onChangeText={setImage} />

			{/* Cooking Time */}
			<Text className='text-lg font-bold text-white mb-2'>Cooking Time</Text>
			<TextInput className='bg-green-500 text-white p-3 rounded-md mb-4' placeholder='Cooking Time in minutes' placeholderTextColor='black' value={cookingTime} onChangeText={setCookingTime} keyboardType='numeric' />

			{/* Servings per Person */}
			<Text className='text-lg font-bold text-white mb-2'>Servings per person</Text>
			<View className='bg-green-500 rounded-md mb-4'>
				<Picker selectedValue={servings} onValueChange={(itemValue) => setServings(itemValue)}>
					<Picker.Item label='Select servings per person' value='' />
					{numberOfPeople.map((num) => (
						<Picker.Item key={num} label={num} value={num} />
					))}
				</Picker>
			</View>

			{/* Ingredients */}
			<Text className='text-lg font-bold text-white mb-2'>Ingredients Needed</Text>
			<TextInput className='bg-green-500 text-white p-3 rounded-md mb-4' placeholder='Ingredients' placeholderTextColor='black' value={ingredients} onChangeText={setIngredients} multiline />

			{/* Recipe Category */}
			<Text className='text-lg font-bold text-white mb-2'>Recipe Category</Text>
			<View className='bg-green-500 rounded-md mb-4'>
				<Picker selectedValue={category} onValueChange={(itemValue) => setCategory(itemValue)}>
					<Picker.Item label='Select recipe category' value='' />
					{categories.map((cat) => (
						<Picker.Item key={cat} label={cat} value={cat} />
					))}
				</Picker>
			</View>

			{/* Buttons */}
			<View className='flex-row justify-between mt-5 mb-12'>
				<TouchableOpacity className='bg-green-700 py-3 px-8 rounded-full' onPress={() => router.back()}>
					<Text className='text-white text-lg font-bold'>Cancel</Text>
				</TouchableOpacity>

				<TouchableOpacity className='bg-green-500 py-3 px-8 rounded-full' onPress={handleCreateRecipe}>
					<Text className='text-white text-lg font-bold'>Create Recipe</Text>
				</TouchableOpacity>
			</View>
		</ScrollView>
	)
}

export default CreateRecipe
