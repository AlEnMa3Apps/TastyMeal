import { View, Text, FlatList, ActivityIndicator, Image, TextInput, TouchableOpacity } from 'react-native'
import React, { useEffect, useState } from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'
import api from '../../../api/api'
import FontAwesome6 from '@expo/vector-icons/FontAwesome6'
import MaterialIcons from '@expo/vector-icons/MaterialIcons'
import FontAwesome from '@expo/vector-icons/FontAwesome'
import { router } from 'expo-router'

const Home = () => {
	const [recipes, setRecipes] = useState([])
	const [loading, setLoading] = useState(true)
	const [error, setError] = useState(null)
	const [searchQuery, setSearchQuery] = useState('')
	const [filteredRecipes, setFilteredRecipes] = useState([])
	const [activeCategory, setActiveCategory] = useState(null)

	useEffect(() => {
		const fetchRecipes = async () => {
			try {
				const response = await api.get('/api/recipes/all')
				setRecipes(response.data)
			} catch (err) {
				setError('Error al cargar las recetas. Inténtalo más tarde.')
				console.error(err)
			} finally {
				setLoading(false)
			}
		}

		fetchRecipes()
	}, [])

	console.log(JSON.stringify(recipes, null, 2))

	useEffect(() => {
		if (activeCategory) {
			setFilteredRecipes(recipes.filter((recipe) => recipe.categoryName === activeCategory))
		} else {
			setFilteredRecipes(recipes)
		}
	}, [activeCategory, recipes])

	if (loading) {
		return (
			<View className='flex-1 justify-center items-center'>
				<ActivityIndicator size='large' color='#0000ff' />
			</View>
		)
	}

	if (error) {
		return (
			<View className='flex-1 justify-center items-center'>
				<Text className='text-red-500 text-lg'>{error}</Text>
			</View>
		)
	}

	const handleSearch = () => {
		console.log('Buscar:', searchQuery) 
	}

	return (
		<SafeAreaView className='flex-1 bg-slate-50'>
			<View className='flex-row justify-between items-center p-4 bg-green-800'>
				<Text className='text-2xl font-bold text-slate-50'>Recipes</Text>
				<View className='flex-row items-center bg-slate-50 rounded-3xl p-2 w-52'>
					<TextInput className='w-40' placeholder='Search any recipe' placeholderTextColor='#9CA3AF' value={searchQuery} onChangeText={setSearchQuery} />
					<TouchableOpacity onPress={handleSearch} className='ml-2 bg-slate-50 rounded-3xl'>
						<FontAwesome name='search' size={20} color='gray' />
					</TouchableOpacity>
				</View>
			</View>
			<FlatList
				ListHeaderComponent={
					<FlatList
						data={recipes.map((item) => item.categoryName)}
						horizontal
						keyExtractor={(item, index) => index.toString()}
						renderItem={({ item }) => (
							<TouchableOpacity
								onPress={() => setActiveCategory(item === activeCategory ? null : item)}
								className={`p-4 mx-2 my-4 rounded-2xl ${item === activeCategory ? 'bg-lime-500' : 'bg-green-800'}`}>
								<Text className={`text-lg font-bold ${item === activeCategory ? 'text-slate-900 ' : 'text-slate-50 '}     `}>{item}</Text>
							</TouchableOpacity>
						)}
						showsHorizontalScrollIndicator={true}
						ItemSeparatorComponent={() => <View className='w-2' />}
					/>
				}
				data={filteredRecipes}
				keyExtractor={(item) => item.id.toString()}
				renderItem={({ item }) => (
					<TouchableOpacity
						onPress={() =>
							router.push({
								pathname: '/(home)/[id]',
								params: { id: item.id }
							})
						}>
						<View className='p-6 mx-6 my-2 border border-green-900 rounded-lg bg-stone-800'>
							<View className='flex-col items-center'>
								<View className='justify-center items-center w-96 h-96 mb-2'>
									<Image source={{ uri: item.imageUrl }} className='w-80 h-80 rounded-lg  border-black border-2' />
								</View>
							</View>
							<Text className='text-3xl font-bold text-slate-100'>{item.title}</Text>
							<Text className='text-base text-gray-300 my-2 '>{item.description}</Text>

							<View className='flex-row my-2'>
								<View className='flex-row my-2 border border-slate-50 py-2 px-4 rounded-3xl'>
									<FontAwesome6 name='clock-four' size={24} color='lightgray' />
									<Text className='text-base text-gray-300 pl-2'>{item.cookingTime} min</Text>
								</View>
								<View className='flex-row my-2 mx-10 border border-slate-50 py-2 px-4 rounded-3xl'>
									<MaterialIcons name='people' size={24} color='lightgray' />
									<Text className='text-base text-gray-300 pl-2'>{item.numPersons}</Text>
								</View>
							</View>
							<View className='flex-row justify-between'>
								<View className=' w-28 p-2 mt-4 rounded-3xl bg-green-800'>
									<Text className='text-lg text-gray-100 text-center font-bold'>{item.categoryName}</Text>
								</View>
								{/* <FontAwesome name='heart' size={24} color='red' /> */}
								<FontAwesome name='heart-o' size={24} color='red' />
							</View>
						</View>
					</TouchableOpacity>
				)}
				ItemSeparatorComponent={() => <View className='h-4' />}
			/>
		</SafeAreaView>
	)
}

export default Home
