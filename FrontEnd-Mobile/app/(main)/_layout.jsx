import React from 'react'
import { Stack, useRouter } from 'expo-router'
import { useEffect } from 'react'
import { setStatusBarStyle } from 'expo-status-bar'
import { useAuth } from '../../context/AuthContext'

const MainLayout = () => {
	const router = useRouter()
	const { token, role } = useAuth()

	useEffect(() => {
		setTimeout(() => {
			setStatusBarStyle('dark')
		}, 0)
	}, [])

	useEffect(() => {
		if (!token || !role) {
			router.replace('/(auth)/login')
		} else {
			// Opcionalmente, redirigir a diferentes rutas dependiendo del rol
		}
	}, [token, role])

	return (
		<Stack>
			<Stack.Screen name='home' options={{ headerShown: false }} />
			<Stack.Screen name='homeVIP' options={{ headerShown: false }} />
		</Stack>
	)
}

export default MainLayout
