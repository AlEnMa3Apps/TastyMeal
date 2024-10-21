import React from 'react'
import { Stack } from 'expo-router'
import { useEffect } from 'react'
import { setStatusBarStyle } from 'expo-status-bar'

const MainLayout = () => {
	useEffect(() => {
		setTimeout(() => {
			setStatusBarStyle('dark')
		}, 0)
	}, [])
	return (
		<>
			<Stack>
				<Stack.Screen name='home' options={{ headerShown: false }} />
			</Stack>
		</>
	)
}

export default MainLayout