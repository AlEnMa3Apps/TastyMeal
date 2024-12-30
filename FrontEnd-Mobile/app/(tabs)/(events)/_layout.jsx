import React from 'react'
import { Stack } from 'expo-router'

const EventLayout = () => {
  return (
    <Stack screenOptions={{ headerShown: true }}>
      <Stack.Screen name='index' options={{ title: 'Events', headerShown: false }} />
      <Stack.Screen name='allEvents' options={{ title: 'All Events' }} />
      <Stack.Screen name='[id]' options={{ title: 'Event Details' }} />
      <Stack.Screen name='myEvents' options={{ title: 'My Events' }} />
    </Stack>
  )
}

export default EventLayout