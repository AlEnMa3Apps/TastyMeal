import { View, Text } from 'react-native'
import React from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'

const Home = () => {
	return (
		<SafeAreaView>
			<View className='items-center justify-center'>
				<Text className='text-3xl mt-24'>home</Text>
			</View>
		</SafeAreaView>
	)
}

export default Home
