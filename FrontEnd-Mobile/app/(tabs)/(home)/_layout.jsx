import { View, Text } from 'react-native'
import React from 'react'
import { Stack } from 'expo-router'

const HomeLayout = () => {
	return (
		<Stack screenOptions={{ headerShown: true }}>
			<Stack.Screen name='index' options={{ title: 'Home', headerShown: false }} />
			<Stack.Screen name='[id]' options={{ title: 'Recipe Details' }} />
		</Stack>
	)
}

export default HomeLayout
