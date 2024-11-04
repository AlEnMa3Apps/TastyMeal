import { View, Text } from 'react-native'
import React from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'

const create = () => {
  return (
    <SafeAreaView>
    <View className='items-center justify-center'>
      <Text className='text-3xl mt-24'>Create</Text>
    </View>
  </SafeAreaView>
  )
}

export default create