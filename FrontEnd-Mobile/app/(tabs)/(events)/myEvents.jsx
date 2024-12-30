import React, { useState, useEffect } from 'react'
import { View, Text, ActivityIndicator, FlatList, TouchableOpacity } from 'react-native'
import { styled } from 'nativewind'
import { useRouter } from 'expo-router'
import api from '../../../api/api'

// Ejemplos de componentes con NativeWind
const StyledView = styled(View)
const StyledText = styled(Text)
const StyledTouchableOpacity = styled(TouchableOpacity)

export default function MyEvents() {
  const [registeredEventsIds, setRegisteredEventsIds] = useState([]) // Aquí guardamos los IDs
  const [eventDetails, setEventDetails] = useState([])               // Aquí los detalles completos
  const [loading, setLoading] = useState(true)
  const router = useRouter()

  useEffect(() => {
    fetchRegisteredEvents()
  }, [])

  /**
   * 1) Llama a GET /api/events/registered -> devuelve array de IDs [1, 2, ...]
   * 2) Por cada ID, llama a GET /api/event/{id} -> array de objetos con datos.
   */
  const fetchRegisteredEvents = async () => {
    try {
      // 1) Obtener array de IDs
      const response = await api.get('/api/events/registered')
      const ids = response.data // p.ej. [1,2,3]
      setRegisteredEventsIds(ids)

      // 2) Por cada ID, llama a /api/event/{id} para obtener el detalle
      const requests = ids.map((eventId) => api.get(`/api/event/${eventId}`))
      const responses = await Promise.all(requests)

      // Extraer data de cada respuesta: [{id, title, date, ...}, {...}, ...]
      const eventsArray = responses.map((res) => res.data)
      setEventDetails(eventsArray)
    } catch (error) {
      console.error('Error al obtener los eventos registrados:', error)
    } finally {
      setLoading(false)
    }
  }

  // Renderiza cada evento de "eventDetails"
  const renderItem = ({ item }) => {
    // Aquí "item" es el objeto con { id, title, date, duration, ... }
    return (
      <StyledTouchableOpacity
        className="mb-3 rounded-lg bg-white p-4"
        // Por si quieres navegar a detalles:
        onPress={() => router.push(`/(events)/${item.id}`)}
      >
        <StyledText className="text-xl text-center font-bold text-gray-800 mb-2">{item.title}</StyledText>
        <StyledText className="text-gray-600 mb-2">Fecha: {item.date}</StyledText>
        <StyledText className="text-gray-600 mb-2">Duración: {item.duration}</StyledText>
        <StyledText className="text-gray-600 mb-4">{item.description}</StyledText>

        <StyledView className="mt-2 rounded bg-green-600 px-2 py-1">
          <StyledText className="text-green-100 text-center text-xl">{item.categoryName}</StyledText>
        </StyledView>
      </StyledTouchableOpacity>
    )
  }

  // Muestra indicador de carga mientras esperamos
  if (loading) {
    return (
      <StyledView className="flex-1 items-center justify-center bg-gray-900 px-4">
        <ActivityIndicator size="large" color="#fff" />
        <StyledText className="mt-2 text-white">Cargando eventos...</StyledText>
      </StyledView>
    )
  }

  // Si no hay eventos registrados
  if (registeredEventsIds.length === 0) {
    return (
      <StyledView className="flex-1 items-center justify-center bg-gray-900 px-4">
        <StyledText className="text-3xl font-bold text-white">My Events</StyledText>
        <StyledText className="mt-4 text-xl text-white">No tienes eventos registrados.</StyledText>
      </StyledView>
    )
  }

  // Render principal con la lista de "eventDetails"
  return (
    <StyledView className="flex-1 bg-gray-900 px-4">
      {/* Título */}
      <StyledView className="items-center py-4">
        <StyledText className="text-2xl font-bold text-white">My Events</StyledText>
      </StyledView>

      {/* Lista de eventos */}
      <FlatList
        data={eventDetails}
        keyExtractor={(item) => item.id.toString()}
        renderItem={renderItem}
      />
    </StyledView>
  )
}