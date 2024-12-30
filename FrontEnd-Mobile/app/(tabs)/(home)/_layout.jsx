/**
 * Importación de módulos necesarios de React y Expo Router.
 *
 * @module react-native
 * @see https://reactnative.dev/
 * @module expo-router
 * @author Manuel García Nieto
 */
import { View, Text } from 'react-native'
import React from 'react'
import { Stack } from 'expo-router'


/**
 * Componente HomeLayout.
 * Define una pila de navegación para las pantallas de inicio y detalles de recetas.
 *
 * @returns {JSX.Element} Componente que define las rutas dentro de la sección "Home".
 */
const HomeLayout = () => {
	return (
		<Stack screenOptions={{ headerShown: true }}>
			<Stack.Screen name='index' options={{ title: 'Home', headerShown: false }} />
			<Stack.Screen name='[id]' options={{ title: 'Recipe Details' }} />
			<Stack.Screen name='report-recipe' options={{ title: 'Report Recipe' }} />
		</Stack>
	)
}

export default HomeLayout
