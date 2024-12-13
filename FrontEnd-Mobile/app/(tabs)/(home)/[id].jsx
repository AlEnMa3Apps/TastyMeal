/**
 * Componente RecipeDetails.
 * Muestra los detalles de una receta específica obtenida del backend.
 *
 * @returns {JSX.Element} Vista con los detalles de la receta o un indicador de carga.
 * @author Manuel García Nieto
 */

// Importación de módulos necesarios
import React, { useEffect, useState } from 'react'
import { View, Text, ActivityIndicator, Image, ScrollView, TextInput, Button } from 'react-native'
import { useRouter, useLocalSearchParams } from 'expo-router'
import api from '../../../api/api'
import FontAwesome from '@expo/vector-icons/FontAwesome'
import { collapseTextChangeRangesAcrossMultipleVersions } from 'typescript'

/**
 * Componente principal RecipeDetails.
 * Obtiene y muestra los detalles de una receta basada en un ID dinámico.
 */
const RecipeDetails = () => {
	//Obtener el parámetro dinámico id de la ruta
	const { id } = useLocalSearchParams()

	// Estados para la receta y el indicador de carga
	const [recipe, setRecipe] = useState(null)
	const [loading, setLoading] = useState(true)
	const [comments, setComments] = useState([])
	const [newComment, setNewComment] = useState('')

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

	// useEffect para cargar los comentarios de la receta al montar el componente
	useEffect(() => {
		const fetchComments = async () => {
			try {
				const response = await api.get(`/api/recipe/${id}/comments`)
				setComments(response.data)
			} catch (err) {
				console.error(err)
			}
		}

		if (id) {
			fetchComments()
		}
	}, [id])

	console.log('este comments falla' + comments)

	// Función para manejar el envío de un nuevo comentario
	const handleCommentSubmit = async () => {
		if (newComment.trim() === '') return

		try {
			await api.post(`/api/recipe/${id}/comment`, {
				text: newComment
			})

			// Limpiar el input
			setNewComment('')

			// Volver a cargar los comentarios
			const response = await api.get(`/api/recipe/${id}/comments`)
			setComments(response.data)
		} catch (err) {
			console.error(err)
		}
	}

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
		<ScrollView className='flex-1 bg-lime-500 px-6'>
			<Image source={{ uri: recipe.imageUrl }} className='w-full h-80 rounded-lg mb-4' />
			<View className='flex-row justify-between items-center mt-0'>
				<Text className='text-4xl font-bold'>{recipe.title}</Text>
				{/* <FontAwesome name='heart' size={24} color='red' /> */}
				<FontAwesome name='heart-o' size={24} color='red' />
			</View>
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

			{/* Sección de Comentarios */}
			<Text className='text-xl font-bold mt-6'>--- Comments ---</Text>
			<TextInput value={newComment} onChangeText={setNewComment} placeholder='Leave a comment...' className='border border-gray-300 rounded p-2 mt-4 bg-white' multiline/>
			<View className='mt-2 mb-4'>
				<Button title='Comment' onPress={handleCommentSubmit} color='#000' />
			</View>

			{comments &&
				comments.length > 0 &&
				comments.map((comment, index) => (
					<View key={index} className='mt-4 bg-white p-3 rounded'>
						<Text className='text-lg'>
							<Text className='font-bold'>{comment.userName}:</Text> {comment.text}
						</Text>
					</View>
				))}
		</ScrollView>
	)
}

export default RecipeDetails
