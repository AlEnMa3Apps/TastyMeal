import React from 'react'
import { View, Text, FlatList, Image, StyleSheet } from 'react-native'

// const recipes = [
//   {
//     id: '1',
//     title: 'Ensalada César',
//     image: require('../../assets/cesar-salad.jpg'),
//   },
//   {
//     id: '2',
//     title: 'Paella Valenciana',
//     image: require('../../assets/paella.jpg'),
//   },
//   {
//     id: '3',
//     title: 'Tacos al Pastor',
//     image: require('../../assets/tacos.jpg'),
//   },
// ];

export default function Home() {
	const renderItem = ({ item }) => (
		<View style={styles.card}>
			<Image source={item.image} style={styles.image} resizeMode='cover' />
			<Text style={styles.title}>{item.title}</Text>
		</View>
	)

	return (
		<View className='flex-1 bg-green-500 items-center justify-center'>
			<Text className='text-4xl mt-52 text-white font-bold'> Tasty Meal Recipes </Text>
			<FlatList
				// data={recipes}
				renderItem={renderItem}
				keyExtractor={(item) => item.id}
				contentContainerStyle={styles.list}
			/>
		</View>
	)
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		paddingTop: 40,
		backgroundColor: '#F'
	},
	header: {
		fontSize: 28,
		fontWeight: 'bold',
		paddingHorizontal: 16,
		marginBottom: 20
	},
	list: {
		paddingHorizontal: 16
	},
	card: {
		marginBottom: 20,
		borderRadius: 8,
		overflow: 'hidden',
		backgroundColor: '#F8F8F8'
	},
	image: {
		width: '100%',
		height: 200
	},
	title: {
		fontSize: 20,
		padding: 16
	}
})
