import { View, Text } from 'react-native'
import React from 'react'
import { Stack } from 'expo-router'

const HomeLayout = () => {
  return (
    <Stack screenOptions={{ headerShown: true }}>
    <Stack.Screen name="index" options={{ title: 'Profile', headerShown: false }} />
    {/* <Stack.Screen name="edit-profile" options={{ title: 'Edit Profile' }} />
    <Stack.Screen name="delete-profile" options={{ title: 'Delete Profile' }} />
    <Stack.Screen name="reset-password" options={{ title: 'Reset Password' }} /> */}
  </Stack>
  )
}

export default HomeLayout