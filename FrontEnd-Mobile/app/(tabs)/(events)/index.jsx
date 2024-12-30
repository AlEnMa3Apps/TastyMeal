import React from 'react'
import { View, Text, TouchableOpacity } from 'react-native'
import { useRouter } from 'expo-router'
import { styled } from 'nativewind'

// Opcional: estilos con NativeWind
const StyledView = styled(View)
const StyledText = styled(Text)
const StyledTouchableOpacity = styled(TouchableOpacity)

/**
 * Pantalla principal de "Events" (index.jsx)
 * Muestra dos botones: All Events y My Events.
 */
export default function EventsIndex() {
	const router = useRouter()

	return (
		<StyledView className='flex-1 items-center justify-center bg-gray-200 px-4'>
			<StyledText className='mb-8 text-5xl font-bold text-gray-800'>Events</StyledText>

			{/* Botón para ir a "All Events" */}
			<StyledTouchableOpacity className='mb-4 w-full rounded-full bg-blue-600 p-4' onPress={() => router.push('/(events)/allEvents')}>
				<StyledText className='text-center text-2xl text-white'>All Events</StyledText>
			</StyledTouchableOpacity>

			{/* Botón para ir a "My Events" */}
			<StyledTouchableOpacity className='w-full rounded-full bg-green-600 p-4 ' onPress={() => router.push('/(events)/myEvents')}>
				<StyledText className='text-center text-2xl text-white'>My Events</StyledText>
			</StyledTouchableOpacity>
		</StyledView>
	)
}
