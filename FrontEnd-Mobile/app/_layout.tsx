import { Stack } from 'expo-router'
import { useEffect } from 'react'
import { setStatusBarStyle } from 'expo-status-bar'

export default function RootLayout() {
	useEffect(() => {
		setTimeout(() => {
			setStatusBarStyle('light')
		}, 0)
	}, [])

	return (
		<Stack>
			<Stack.Screen name='index' options={{ headerTitle: 'Welcome', headerTintColor: '#fff', headerStyle: { backgroundColor: '#000' }, headerBackVisible: false, gestureEnabled: false }} />
			<Stack.Screen name='(auth)' options={{ headerShown: false }} />
			<Stack.Screen name='(main)' options={{ headerShown: false }} />
		</Stack>
	)
}
