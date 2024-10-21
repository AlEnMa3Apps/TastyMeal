import React from 'react'
import { Stack } from 'expo-router'
import { useEffect } from 'react'
import { setStatusBarStyle } from 'expo-status-bar'

const AuthLayout = () => {
	useEffect(() => {
		setTimeout(() => {
			setStatusBarStyle('dark')
		}, 0)
	}, [])
	return (
		<>
			<Stack>
				<Stack.Screen name='login' options={{ headerShown: false }} />
				<Stack.Screen name='signUp' options={{ headerShown: false }} />
			</Stack>
		</>
	)
}

export default AuthLayout
