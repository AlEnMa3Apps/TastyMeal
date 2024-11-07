import { View, Text } from 'react-native'
import React from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'

const Likes = () => {
  return (
    <SafeAreaView className='bg-gray-600 h-full'>
    <View className='items-center justify-center bg-slate-400 h-full'>
      <Text className='text-3xl mt-24'>Likes</Text>
    </View>
  </SafeAreaView>
  )
}

export default Likes