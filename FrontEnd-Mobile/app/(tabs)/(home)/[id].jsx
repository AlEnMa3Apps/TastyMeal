/**
 * Componente RecipeDetails.
 * Muestra los detalles de una receta específica obtenida del backend.
 *
 * @returns {JSX.Element} Vista con los detalles de la receta o un indicador de carga.
 * @author Manuel García Nieto
 */

// Importación de módulos necesarios
import React, { useEffect, useState, useContext } from 'react'
import { View, Text, ActivityIndicator, Image, ScrollView, TextInput, Button, Alert } from 'react-native'
import { useRouter, useLocalSearchParams } from 'expo-router'
import api from '../../../api/api'
import FontAwesome from '@expo/vector-icons/FontAwesome'
import { collapseTextChangeRangesAcrossMultipleVersions } from 'typescript'
import { UserContext } from '../../../context/UserContext'

/**
 * Componente principal RecipeDetails.
 * Obtiene y muestra los detalles de una receta basada en un ID dinámico.
 */
export const RecipeDetails = () => {
	//Obtener el parámetro dinámico id de la ruta
	const { id } = useLocalSearchParams()
	const { username } = useContext(UserContext)

	// Estados para la receta y el indicador de carga
	const [recipe, setRecipe] = useState(null)
	const [loading, setLoading] = useState(true)
	const [comments, setComments] = useState([])
	const [newComment, setNewComment] = useState('')

	// Log para verificar el ID recibido
	console.log(id)

	// Estados para edición de comentarios
	const [editingCommentId, setEditingCommentId] = useState(null)
	const [editingCommentText, setEditingCommentText] = useState('')

	// Estado para indicar si la receta es favorita
	const [isFavorite, setIsFavorite] = useState(false)

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

		// Verifica si la receta está en la lista de favoritos
		const checkIfFavorite = async () => {
			try {
				const response = await api.get('/api/recipes/favorite')
				// response.data debería ser la lista de recetas favoritas
				const favoriteRecipeIds = response.data

			  // Chequea si el ID actual está en ese array
				const found = favoriteRecipeIds.includes(Number(id))
				setIsFavorite(found)
			} catch (error) {
				console.error(error)
			}
		}

		// Ejecutar la función solo si id está disponible
		if (id) {
			fetchRecipeDetails()
			checkIfFavorite()
		}
	}, [id])

	// useEffect para cargar los comentarios de la receta al montar el componente
	useEffect(() => {
		const fetchComments = async () => {
			try {
				const response = await api.get(`/api/recipe/${id}/comments`)
				setComments(response.data)
				console.log(response.data)
			} catch (err) {
				console.error(err)
			}
		}

		if (id) {
			fetchComments()
		}
	}, [id])

	console.log(id)

	// Función para manejar el envío de un nuevo comentario
	const handleCommentSubmit = async () => {
		if (newComment.trim() === '') return

		try {
			await api.post(`/api/recipe/${id}/comment`, {
				comment: newComment
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

	// Función para manejar la eliminación de un comentario
	const handleDeleteComment = async (commentId) => {
		try {
			await api.delete(`/api/comment/${commentId}`)
			const response = await api.get(`/api/recipe/${id}/comments`)
			setComments(response.data)
		} catch (err) {
			console.error(err)
		}
	}

	// Funciones para editar comentarios
	const startEditingComment = (comment) => {
		setEditingCommentId(comment.id)
		setEditingCommentText(comment.comment)
	}

	const cancelEditComment = () => {
		setEditingCommentId(null)
		setEditingCommentText('')
	}

	const saveEditedComment = async () => {
		if (!editingCommentId || editingCommentText.trim() === '') return

		try {
			// PUT /api/comment/{id}
			await api.put(`/api/comment/${editingCommentId}`, {
				comment: editingCommentText
			})

			// Recargar comentarios
			const response = await api.get(`/api/recipe/${id}/comments`)
			setComments(response.data)

			// Limpiar estado de edición
			setEditingCommentId(null)
			setEditingCommentText('')
		} catch (err) {
			console.error(err)
		}
	}

	// Función para manejar la adición o eliminación de una receta a la lista de favoritos
	const handleToggleFavorite = async () => {
		try {
			if (!isFavorite) {
				// Añadir a favoritos
				await api.post(`/api/recipe/${id}/favorite`)
				setIsFavorite(true)
			} else {
				// Eliminar de favoritos
				await api.delete(`/api/recipe/${id}/favorite`)
				setIsFavorite(false)
			}
		} catch (error) {
			console.error(error)
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

	console.log('que usuario es  ' + username)
	// Renderizar los detalles de la receta
	return (
		<ScrollView className='flex-1 bg-lime-500 px-6'>
			<Image source={{ uri: recipe.imageUrl }} className='w-full h-80 rounded-lg mb-4' />
			<View className='flex-colum items-start mt-0'>
				<Text className='text-4xl font-bold'>{recipe.title}</Text>
				{/* Icono para favoritos */}
				<FontAwesome name={isFavorite ? 'heart' : 'heart-o'} size={24} color='red' style={{ marginLeft: 10, marginTop: 10 }} onPress={handleToggleFavorite} />
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
			<Text className='text-2xl font-bold mt-6 text-center'>--- Comments ---</Text>
			<TextInput value={newComment} onChangeText={setNewComment} placeholder='Leave a comment...' className='border border-gray-300 rounded-lg p-2 mt-4 bg-white' multiline />
			<View className='mt-2 mb-4'>
				<Button title='Comment' onPress={handleCommentSubmit} color='#000' />
			</View>

			{comments &&
				comments.length > 0 &&
				comments.map((comment, index) => (
					<View key={index} className='mt-4 bg-white p-3 rounded-lg mb-10'>
						{editingCommentId === comment.id ? (
							<>
								{/* Modo edición */}
								<TextInput value={editingCommentText} onChangeText={setEditingCommentText} placeholder='Edit your comment...' className='border border-gray-300 rounded-lg p-2 mt-4 bg-white' multiline />
								<View className='flex-row mt-2 mb-2 justify-evenly'>
									<Button title='Save' onPress={saveEditedComment} color='#000' />
									<Button title='Cancel' onPress={cancelEditComment} color='#000' />
								</View>
							</>
						) : (
							<>
								{/* Modo visualización normal */}
								<Text className='text-lg mb-4'>
									<Text className='font-bold'>{comment.author}:</Text> {comment.comment}
								</Text>
								{comment.author === username && (
									<View className='flex-row mt-2 mb-2 justify-evenly'>
										<Button title='Edit' onPress={() => startEditingComment(comment)} color='#000' />
										<Button
											title='Delete'
											onPress={() => {
												Alert.alert('Delete Confirmation', 'Are you sure you want to delete this comment?', [
													{ text: 'Cancel', style: 'cancel' },
													{
														text: 'Delete',
														style: 'destructive',
														onPress: () => handleDeleteComment(comment.id)
													}
												])
											}}
											color='#000'
										/>
									</View>
								)}
							</>
						)}
					</View>
				))}
		</ScrollView>
	)
}

export default RecipeDetails
