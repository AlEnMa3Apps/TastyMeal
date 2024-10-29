/**
 * @file HomeScreen.jsx
 * @description Componente de pantalla de inicio que muestra recetas y permite cerrar sesión.
 * @autor Manuel García Nieto
 */

import React from 'react'
import { View, Text, FlatList, Image, TouchableOpacity, ScrollView } from 'react-native'
import { useRouter } from 'expo-router'
import { useAuth } from '../../context/AuthContext'

/**
 * @constant
 * @name recipes
 * @description Lista de recetas para mostrar en la pantalla de inicio.
 * @type {Array<Object>}
 */
const recipes = [
	{
		id: '1',
		title: 'Ensalada César',
		image: require('../../assets/images/cesar-salad.png')
	},
	{
		id: '2',
		title: 'Paella Valenciana',
		image: require('../../assets/images/paella.png')
	},
	{
		id: '3',
		title: 'Tacos al Pastor',
		image: require('../../assets/images/tacos.png')
	}
]
/**
 * @component
 * @name HomeScreen
 * @description
 * Componente de pantalla de inicio que muestra una lista de recetas y permite al usuario cerrar sesión.
 *
 * @returns {JSX.Element} La interfaz de usuario de la pantalla de inicio.
 */
export default function HomeScreen() {
		/**
	 * @hook
	 * @name useRouter
	 * @description
	 * Hook para manejar la navegación dentro de la aplicación.
	 *
	 * @type {Object}
	 * @property {Function} replace Función para reemplazar la pantalla actual.
	 */
	const router = useRouter()
		/**
	 * @hook
	 * @name useAuth
	 * @description
	 * Hook personalizado para acceder al contexto de autenticación.
	 *
	 * @typedef {Object} AuthContext
	 * @property {Function} logout Función para cerrar sesión.
	 * @property {string} token Token de autenticación del usuario.
	 * @property {string} role Rol del usuario.
	 */
	const { logout, token, role } = useAuth()

	/**
	 * @function handleLogout
	 * @description
	 * Maneja el proceso de cierre de sesión del usuario.
	 */
	const handleLogout = () => {
		logout()
		router.replace('/')
	}
	
	/**
	 * @function renderItem
	 * @description
	 * Renderiza cada ítem de la lista de recetas.
	 *
	 * @param {Object} param0
	 * @param {Object} param0.item - El ítem de receta a renderizar.
	 * @returns {JSX.Element} El componente de receta.
	 */
	const renderItem = ({ item }) => (
		<View className='mb-5 rounded-lg overflow-hidden bg-gray-200'>
			<Image source={item.image} className='w-full h-48' resizeMode='cover' />
			<Text className='text-xl p-4'>{item.title}</Text>
		</View>
	)

	return (
		<View className='flex-1 bg-green-500 items-center justify-center p-5'>
			<View className='pt-5 px-4 flex-col justify-center items-center mt-64'>
				<Text className='text-4xl text-white font-bold mb-5'>Tasty Meal Recipes</Text>
				<Text className='text-4xl text-black font-bold'>Normal User</Text>
				<TouchableOpacity onPress={handleLogout} className='py-2 px-3 mt-5 bg-red-600 rounded-lg'>
					<Text className='text-white font-semibold text-lg shadow-lg shadow-black'>Log Out</Text>
				</TouchableOpacity>
			</View>
			<View className='pt-5 px-8 mt-5' >
				<FlatList data={recipes} renderItem={renderItem} keyExtractor={(item) => item.id} contentContainerStyle={{ paddingHorizontal: 16, paddingTop: 20 }} />
			</View>
		</View>
	)
}
