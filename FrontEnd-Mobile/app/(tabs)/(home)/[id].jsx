/**
 * Componente RecipeDetails.
 * Muestra los detalles de una receta específica obtenida del backend.
 *
 * @returns {JSX.Element} Vista con los detalles de la receta o un indicador de carga.
 * @author Manuel García Nieto
 */

// Importación de módulos necesarios
import React, { useEffect, useState } from 'react'
import { View, Text, ActivityIndicator, Image } from 'react-native'
import { useRouter, useLocalSearchParams } from 'expo-router'
import api from '../../../api/api'
import FontAwesome from '@expo/vector-icons/FontAwesome'

/**
 * Componente principal RecipeDetails.
 * Obtiene y muestra los detalles de una receta basada en un ID dinámico.
 */
const RecipeDetails = () => {
	// Hook de redirección y hook de búsqueda local
	//Obtener el parámetro dinámico id de la ruta
	const { id } = useLocalSearchParams()

	// Estados para la receta y el indicador de carga
	const [recipe, setRecipe] = useState(null)
	const [loading, setLoading] = useState(true)

	// Log para verificar el ID recibido
	console.log(id)

	// useEffect para cargar los detalles de la receta al montar el componente
	useEffect(() => {
		/**
		 * Función para obtener los detalles de la receta desde el backend.
		 * Llama al endpoint `/api/recipe/{id}`.
		 */
		const fetchRecipeDetails = async () => {
			try {
				// Solicitud GET al backend para obtener los detalles de la receta
				const response = await api.get(`/api/recipe/${id}`)
				setRecipe(response.data)
			} catch (err) {
				console.error(err)
			} finally {
				setLoading(false)
			}
		}

		// Ejecutar la función solo si id está disponible
		if (id) {
			fetchRecipeDetails()
		}
	}, [id])

	// Mostrar un indicador de carga mientras los datos se obtienen del backend
	if (loading) {
		return (
			<View className='flex-1 justify-center items-center'>
				<ActivityIndicator size='large' color='#0000ff' />
			</View>
		)
	}

	// Mostrar un mensaje de error si no se puede cargar la receta
	if (!recipe) {
		return (
			<View className='flex-1 justify-center items-center'>
				<Text className='text-red-500'>Unable to load recipe.</Text>
			</View>
		)
	}

	// Renderizar los detalles de la receta
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
