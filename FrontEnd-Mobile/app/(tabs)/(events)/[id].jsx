import React, { useState, useEffect } from 'react'
import { View, Text, ActivityIndicator, TouchableOpacity } from 'react-native'
import { useLocalSearchParams } from 'expo-router'
import Fontisto from '@expo/vector-icons/Fontisto'
import api from '../../../api/api' // Ajusta la ruta a tu archivo api

export default function EventDetails() {
	const { id } = useLocalSearchParams()

	const [event, setEvent] = useState(null)
	const [loading, setLoading] = useState(true)
	const [isRegistered, setIsRegistered] = useState(false) // Controla si el usuario está registrado en este evento

	// 1. Obtener los datos del evento (titulo, fecha, etc.)
	useEffect(() => {
		const fetchEvent = async () => {
			try {
				const response = await api.get(`/api/event/${id}`)
				setEvent(response.data)
			} catch (error) {
				console.error('Error fetching event by ID:', error)
			} finally {
				setLoading(false)
			}
		}

		if (id) {
			fetchEvent()
		}
	}, [id])

	// 2. Verificar si este evento está registrado por el usuario
	useEffect(() => {
		const checkRegistration = async () => {
			try {
				const response = await api.get('/api/events/registered')
				const registeredEvents = response.data
				const found = registeredEvents.some((evt) => evt === Number(id))
				setIsRegistered(found)
			} catch (err) {
				console.error('Error checking registration:', err)
			}
		}

		if (id) {
			checkRegistration()
		}
	}, [id])

	// 3. Manejar el registro/desregistro (toggle)
	const handleToggleRegistration = async () => {
		try {
			if (!isRegistered) {
				// Registrar al usuario: POST /api/event/{id}/register
				await api.post(`/api/event/${id}/register`)
				setIsRegistered(true)
			} else {
				// Desregistrar al usuario: DELETE /api/event/{id}/unregister
				await api.delete(`/api/event/${id}/unregister`)
				setIsRegistered(false)
			}
		} catch (err) {
			console.error('Error toggling registration:', err)
		}
	}

	// Muestra loading mientras se obtienen los datos del evento
	if (loading) {
		return (
			<View className='flex-1 items-center justify-center bg-gray-900 px-4'>
				<ActivityIndicator size='large' color='#ffffff' />
				<Text className='text-white mt-2'>Loading event details...</Text>
			</View>
		)
	}

	// Si no encontramos el evento (event === null)
	if (!event) {
		return (
			<View className='flex-1 items-center justify-center bg-gray-900 px-4'>
				<Text className='text-white'>No se pudo cargar el evento o no existe.</Text>
			</View>
		)
	}

	// Renderiza detalles del evento y el icono
	return (
		<View className='flex-1 items-center justify-center bg-gray-900 px-4'>
			<Text className='mb-4 text-3xl font-bold text-white'>Event Details</Text>

			{/* Datos del evento */}
			<Text className='mb-2 text-lg text-white'>Título: {event.title}</Text>
			<Text className='mb-2 text-lg text-white'>Fecha: {event.date}</Text>
			<Text className='mb-2 text-lg text-white'>Duración: {event.duration}</Text>
			<Text className='mb-2 text-lg text-white'>Descripción: {event.description}</Text>
			<View className='my-5 rounded-lg bg-green-100 px-3 py-2'>
				<Text className='text-lg font-semibold text-green-900'>{event.categoryName}</Text>
			</View>

			{/* Icono para registrarse/des-registrarse */}
			<TouchableOpacity onPress={handleToggleRegistration} testID='registerButton'>
				<Fontisto
					// Cambiamos el icono según si está registrado o no
					name={isRegistered ? 'checkbox-active' : 'checkbox-passive'}
					size={28}
					color='white'
				/>
			</TouchableOpacity>

			{/* Texto informativo */}
			<Text className='mt-2 text-white'>{isRegistered ? 'Ya estás apuntado a este evento' : 'No estás apuntado a este evento'}</Text>
		</View>
	)
}
