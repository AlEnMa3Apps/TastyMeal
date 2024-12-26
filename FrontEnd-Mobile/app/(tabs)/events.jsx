import React, { useState, useEffect } from 'react'
import { View, Text, TextInput, ActivityIndicator, FlatList, TouchableOpacity } from 'react-native'
import { styled } from 'nativewind'

// Ejemplo de “styled” con NativeWind (opcional)
const StyledView = styled(View)
const StyledText = styled(Text)
const StyledTextInput = styled(TextInput)
const StyledTouchableOpacity = styled(TouchableOpacity)

/**
 * Pantalla básica de "Events" usando NativeWind.
 */
export default function Events() {
  const [events, setEvents] = useState([])
  const [filteredEvents, setFilteredEvents] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchQuery, setSearchQuery] = useState('')

  useEffect(() => {
    // Aquí llamarías a tu API o servicio local para obtener la lista de eventos
    // Ejemplo simulado:
    const mockEvents = [
      { id: 1, title: 'Evento A', description: 'Descripción del evento A' },
      { id: 2, title: 'Evento B', description: 'Descripción del evento B' },
      { id: 3, title: 'Evento C', description: 'Descripción del evento C' }
    ]
    setTimeout(() => {
      setEvents(mockEvents)
      setFilteredEvents(mockEvents)
      setLoading(false)
    }, 1000)
  }, [])

  // Filtrar eventos al cambiar la búsqueda
  useEffect(() => {
    if (!searchQuery) {
      setFilteredEvents(events)
    } else {
      const filtered = events.filter((evt) =>
        evt.title.toLowerCase().includes(searchQuery.toLowerCase())
      )
      setFilteredEvents(filtered)
    }
  }, [searchQuery, events])

  const renderEventItem = ({ item }) => (
    <StyledTouchableOpacity
      className="mb-2 rounded-md bg-white p-4"
      onPress={() => console.log('Navegar al detalle de:', item.id)}
    >
      <StyledText className="text-lg font-bold text-gray-800">{item.title}</StyledText>
      <StyledText className="mt-1 text-gray-600">{item.description}</StyledText>
    </StyledTouchableOpacity>
  )

  // Muestra un indicador de carga mientras esperamos los eventos
  if (loading) {
    return (
      <StyledView className="flex-1 items-center justify-center bg-gray-100">
        <ActivityIndicator size="large" color="#0000ff" />
      </StyledView>
    )
  }

  return (
    <StyledView className="flex-1 bg-gray-100">
      {/* Encabezado */}
      <StyledView className="bg-green-500 p-4">
        <StyledText className="text-xl font-bold text-white pt-10">Eventos</StyledText>
      </StyledView>

      {/* Barra de búsqueda */}
      <StyledView className="p-4">
        <StyledTextInput
          className="rounded-md bg-white p-3 text-gray-800"
          placeholder="Buscar evento..."
          value={searchQuery}
          onChangeText={setSearchQuery}
        />
      </StyledView>

      {/* Lista de eventos */}
      <StyledView className="flex-1 px-4">
        <FlatList
          data={filteredEvents}
          keyExtractor={(item) => item.id.toString()}
          renderItem={renderEventItem}
        />
      </StyledView>
    </StyledView>
  )
}