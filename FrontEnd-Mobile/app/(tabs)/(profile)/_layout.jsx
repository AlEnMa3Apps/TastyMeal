/**
 * Componente ProfileLayout.
 * Configura una pila de navegación para las pantallas relacionadas con el perfil de usuario.
 *
 * @returns {JSX.Element} Componente que define las rutas de navegación dentro de la sección "Profile".
 * @autor Manuel García Nieto
 */

// Importación de módulos necesarios para la creación del componente.
import { View, Text } from 'react-native'
import React from 'react'
import { Stack } from 'expo-router'

/**
 * Componente principal ProfileLayout.
 * Define una pila de navegación para las pantallas relacionadas con el perfil de usuario.
 */
const ProfileLayout = () => {
	return (
		/**
		 * Componente Stack de expo-router que agrupa las pantallas en una pila de navegación.
		 *
		 * @screenOptions Configuración predeterminada para todas las pantallas en esta pila.
		 * - headerShown: true muestra el encabezado de navegación de forma predeterminada.
		 */
		<Stack screenOptions={{ headerShown: true }}>
			<Stack.Screen name='index' options={{ title: 'Profile', headerShown: false }} />
			<Stack.Screen name='edit-profile' options={{ title: 'Edit Profile' }} />
			<Stack.Screen name='reset-password' options={{ title: 'Reset Password' }} />
		</Stack>
	)
}

export default ProfileLayout
