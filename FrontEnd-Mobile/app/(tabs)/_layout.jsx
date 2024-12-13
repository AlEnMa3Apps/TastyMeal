/**
 * Componente TabsLayout.
 * Proporciona un diseño de navegación por pestañas para la aplicación.
 * Cada pestaña corresponde a una sección principal de la app: Home, Recipe, Likes y Profile.
 *
 * @returns {JSX.Element} Diseño de pestañas con iconos y estilos personalizados.
 * @autor Manuel García Nieto
 */
import React from 'react'
import { useAuth } from '../../context/AuthContext'
import { Tabs } from 'expo-router'
import { Ionicons, FontAwesome5 } from '@expo/vector-icons'

const TabsLayout = () => {
	const { userIsAuthenticated } = useAuth()
	console.log(userIsAuthenticated())

	return (
		<>
			<Tabs
				screenOptions={{
					tabBarActiveTintColor: 'darkorange',
					tabBarInactiveTintColor: 'black',
					headerShown: false,
					tabBarStyle: {
						borderTopColor: 'black',
						borderTopWidth: 1,
						paddingBottom: 20,
						height: 70,
						elevation: 0
					}
				}}>
				<Tabs.Screen
					name='(home)'
					options={{
						title: 'Home',
						headerShown: false,
						tabBarIcon: ({ color }) => <Ionicons name='home-sharp' size={28} color={color} />
					}}
				/>
				<Tabs.Screen
					name='(recipe)'
					options={{
						title: 'Recipe',
						headerShown: false,
						tabBarIcon: ({ color }) => <Ionicons name='restaurant' size={28} color={color} />
					}}
				/>
				<Tabs.Screen
					name='likes'
					options={{
						title: 'Likes',
						headerShown: false,
						tabBarIcon: ({ color }) => <Ionicons name='heart-sharp' size={28} color={color} />
					}}
				/>
				<Tabs.Screen
					name='(profile)'
					options={{
						title: 'Profile',
						headerShown: false,
						tabBarIcon: ({ color }) => <FontAwesome5 name='user-alt' size={28} color={color} />
					}}
				/>
			</Tabs>
		</>
	)
}

export default TabsLayout
