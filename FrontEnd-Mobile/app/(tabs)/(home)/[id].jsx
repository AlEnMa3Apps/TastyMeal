import React, { useEffect, useState } from 'react'
import { View, Text, ActivityIndicator, Image } from 'react-native'
import { useRouter, useLocalSearchParams } from 'expo-router'
import api from '../../../api/api'
import FontAwesome from '@expo/vector-icons/FontAwesome'

const RecipeDetails = () => {
	const { id } = useLocalSearchParams()
	const [recipe, setRecipe] = useState(null)
	const [loading, setLoading] = useState(true)

	console.log(id)

	useEffect(() => {
		const fetchRecipeDetails = async () => {
			try {
				const response = await api.get(`/api/recipe/${id}`)
				setRecipe(response.data)
			} catch (err) {
				console.error(err)
			} finally {
				setLoading(false)
			}
		}

		if (id) {
			fetchRecipeDetails()
		}
	}, [id])

	if (loading) {
		return (
			<View className='flex-1 justify-center items-center'>
				<ActivityIndicator size='large' color='#0000ff' />
			</View>
		)
	}

	if (!recipe) {
		return (
			<View className='flex-1 justify-center items-center'>
				<Text className='text-red-500'>Unable to load recipe.</Text>
			</View>
		)
	}

	return (
		<View className='flex-1 bg-lime-500 p-6'>
			<Image source={{ uri: recipe.imageUrl }} className='w-full h-80 rounded-lg mb-4' />
			<Text className='text-4xl font-bold'>{recipe.title}</Text>
			<Text className='text-lg text-gray-950 mt-4'>{recipe.description}</Text>
			<Text className='text-lg text-gray-950 mt-2'>
				<Text className='font-bold'>Ingredients: </Text>
				{recipe.ingredients}
			</Text>
			<Text className='text-lg text-gray-950 mt-2'>
				<Text className='font-bold'>Cooking Time: </Text>
				{recipe.cookingTime} minutes
			</Text>
			<Text className='text-lg text-gray-950 mt-2'>
				<Text className='font-bold'>Servings: </Text>
				{recipe.numPersons === 1 ? `${recipe.numPersons} person` : `${recipe.numPersons} people`}
			</Text>
			<Text className='text-lg text-gray-950 mt-2'>
				<Text className='font-bold'>Type of food: </Text>
				{recipe.categoryName}
			</Text>
			<View className='flex-row justify-end items-center mt-4'>
				{/* <FontAwesome name='heart' size={24} color='red' /> */}
				<FontAwesome name='heart-o' size={24} color='red' />
			</View>
		</View>
	)
}

export default RecipeDetails
