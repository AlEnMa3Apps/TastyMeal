/**
 * Este componente no está implementado todavía.
 * @author Manuel García Nieto
 */

import React, { useEffect, useState } from 'react'
import { View, Text, ActivityIndicator, FlatList, Image, TouchableOpacity } from 'react-native'
import { styled } from 'nativewind'
import { useRouter } from 'expo-router' // Hook para navegar entre pantallas
import { useIsFocused } from '@react-navigation/native' // Hook para saber si la pantalla está en foco
import api from '../../api/api'

const StyledView = styled(View)
const StyledText = styled(Text)
const StyledImage = styled(Image)
const StyledTouchableOpacity = styled(TouchableOpacity)

const LikesScreen = () => {
	const [favoriteRecipes, setFavoriteRecipes] = useState([])
	const [loading, setLoading] = useState(true)
	const router = useRouter() // Instancia del hook para navegar entre pantallas

	const isFocused = useIsFocused() // Devuelve true cuando la pantalla está en foco

	// Cada vez que isFocused cambie a true, cargamos la lista
	useEffect(() => {
		if (isFocused) {
			fetchFavoriteRecipes()
		}
	}, [isFocused])

	const fetchFavoriteRecipes = async () => {
		setLoading(true)
		try {
			const { data: favoriteRecipeIds } = await api.get('/api/recipes/favorite')
			const requests = favoriteRecipeIds.map((id) => api.get(`/api/recipe/${id}`))
			const responseArray = await Promise.all(requests)
			const fetchedRecipes = responseArray.map((res) => res.data)
			setFavoriteRecipes(fetchedRecipes)
		} catch (error) {
			console.error('Error fetching favorite recipes:', error)
		} finally {
			setLoading(false)
		}
	}

	// Función para navegar a los detalles
	const goToRecipeDetails = (recipeId) => {
		// Navega a /home/[id], reemplazando [id] con recipeId
		router.push(`/(home)/${recipeId}`)
	}

	const renderFavoriteItem = ({ item }) => {
		return (
			<StyledTouchableOpacity className='mb-3 rounded-lg bg-white p-4' onPress={() => goToRecipeDetails(item.id)}>
				<StyledText className='text-lg font-bold text-gray-800'>{item.title}</StyledText>
				{item.imageUrl && <StyledImage source={{ uri: item.imageUrl }} className='mt-2 h-36 w-full rounded-lg' resizeMode='cover' />}
				<StyledText className='mt-2 text-gray-700'>{item.description}</StyledText>
			</StyledTouchableOpacity>
		)
	}

	if (loading) {
		return (
			<StyledView className='flex-1 items-center justify-center'>
				<ActivityIndicator size='large' color='#0000ff' />
			</StyledView>
		)
	}

	if (favoriteRecipes.length === 0) {
		return (
			<StyledView className='flex-1 items-center justify-center bg-slate-900'>
				<StyledText className='text-2xl text-gray-100'>No tienes recetas favoritas.</StyledText>
			</StyledView>
		)
	}

	return (
		<StyledView className='flex-1 bg-slate-900 p-4 mt-10'>
			<FlatList data={favoriteRecipes} keyExtractor={(item) => item.id.toString()} renderItem={renderFavoriteItem} />
		</StyledView>
	)
}

export default LikesScreen
