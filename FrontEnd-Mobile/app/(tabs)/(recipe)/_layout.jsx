/**
 * Componente RecipeLayout.
 * Define una pila de navegación (Stack) para gestionar las pantallas relacionadas con las recetas.
 *
 * @returns {JSX.Element} Pila de navegación para las pantallas de recetas.
 * @autor Manuel García Nieto
 */
// Importación de módulos necesarios para la creación del componente.
import { View, Text } from 'react-native'
import React from 'react'
import { Stack } from 'expo-router'

/**
 * Componente principal RecipeLayout.
 * Configura las rutas y encabezados para las diferentes pantallas de recetas.
 */
const RecipeLayout = () => {
	return (
		<Stack screenOptions={{ headerShown: true }}>
			<Stack.Screen name='index' options={{ title: 'Recipe', headerShown: false }} />
			<Stack.Screen name='create-recipe' options={{ title: 'Create Recipe' }} />
			<Stack.Screen name='edit-recipe' options={{ title: 'Edit Recipe' }} />
			<Stack.Screen name='edit-recipe-detail' options={{ title: 'Edit Recipe Detail' }} />
			<Stack.Screen name='delete-recipe' options={{ title: 'Delete Recipe' }} />
		</Stack>
	)
}

export default RecipeLayout
