import React, { useState, useEffect } from 'react'
import { View, Text, TextInput, ActivityIndicator, FlatList, TouchableOpacity } from 'react-native'
import { styled } from 'nativewind'
import api from '../../../api/api'
import { router } from 'expo-router'

// Ejemplo de “styled” con NativeWind (opcional)
const StyledView = styled(View)
const StyledText = styled(Text)
const StyledTextInput = styled(TextInput)
const StyledTouchableOpacity = styled(TouchableOpacity)

/**
 * Pantalla básica de "Events" usando NativeWind.
 */
export default function AllEvents() {
	const [events, setEvents] = useState([])
	const [filteredEvents, setFilteredEvents] = useState([])
	const [loading, setLoading] = useState(true)
	const [searchQuery, setSearchQuery] = useState('')

	useEffect(() => {
		const fetchEvents = async () => {
			try {
				const response = await api.get('/api/event/all')
				const data = response.data
				console.log('Data de eventos:', data)
				setEvents(data)
				setFilteredEvents(data)
			} catch (err) {
				console.error('Error al obtener eventos:', err)
			} finally {
				setLoading(false)
			}
		}

		fetchEvents()
	}, [])

	// Filtrar eventos al cambiar la búsqueda
	useEffect(() => {
		if (!searchQuery) {
			setFilteredEvents(events)
		} else {
			const query = searchQuery.toLowerCase()
			const filtered = events.filter((evt) => evt.title.toLowerCase().includes(query))
			setFilteredEvents(filtered)
		}
	}, [searchQuery, events])

	const renderEventItem = ({ item }) => (
		<StyledTouchableOpacity
			className='mb-3 rounded-lg bg-white p-4 shadow-lg'
			onPress={() =>
				router.push({
					pathname: '/(events)/[id]',
					params: { id: item.id }
				})
			}>
			{/* Encabezado: título + badge de categoría */}
			<StyledView className='mb-2 items-center justify-between'>
				<StyledText className='text-xl font-semibold text-gray-800 text-left'>{item.title}</StyledText>
				{/* Badge de categoría */}
				<StyledView className='rounded-full bg-green-100 px-3 py-2 my-5'>
					<StyledText className='text-xs font-semibold text-green-700'>{item.categoryName}</StyledText>
				</StyledView>
			</StyledView>
			{/* Fecha y duración en letras pequeñas */}
			<StyledText className='text-sm text-gray-500'>Date: {item.date}</StyledText>
			<StyledText className='text-sm text-gray-500'>Duration: {item.duration}</StyledText>
			{/* Descripción */}
			<StyledText className='mt-2 text-base text-gray-700'>{item.description}</StyledText>
		</StyledTouchableOpacity>
	)

	// Muestra un indicador de carga mientras esperamos los eventos
	if (loading) {
		return (
			<StyledView className='flex-1 items-center justify-center bg-gray-100'>
				<ActivityIndicator size='large' color='#0000ff' />
			</StyledView>
		)
	}

	return (
		<StyledView className='flex-1 bg-gray-900'>
			{/* Barra de búsqueda */}
			<StyledView className='p-4'>
				<StyledTextInput className='rounded-md bg-white p-3 text-gray-800' placeholder='Search event...' value={searchQuery} onChangeText={setSearchQuery} />
			</StyledView>

			{/* Lista de eventos */}
			<StyledView className='flex-1 px-4'>
				<FlatList data={filteredEvents} keyExtractor={(item) => item.id?.toString()} renderItem={renderEventItem} />
			</StyledView>
		</StyledView>
	)
}
