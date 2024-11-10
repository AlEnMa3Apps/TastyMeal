import { View, Text, TouchableOpacity, TextInput, Alert } from 'react-native'
import React, { useState } from 'react'
import api from '../../../api/api'

const ResetPassword = () => {
	const [newPassword, setNewPassword] = useState('')
	const [confirmPassword, setConfirmPassword] = useState('')

	const handleResetPassword = async () => {
		if (newPassword !== confirmPassword) {
			Alert.alert('Passwords do not match', 'Please make sure both passwords are the same.')
			return
		}

    if (newPassword.length < 4 || confirmPassword.length < 4) {
			Alert.alert('Error', 'Password must be at least 4 characters long.')
			return
		}

		 try {
        // Realiza la solicitud PATCH al endpoint /user con la nueva contraseña
        const response = await api.patch('/api/user', {
            password: newPassword
        });

        if (response.status === 200) {
            Alert.alert("Success", "Password has been reset successfully.");
        } else {
            Alert.alert("Error", "Failed to reset password. Please try again.");
        }
    } catch (error) {
        console.error("Error resetting password:", error);
        Alert.alert("Error", "An error occurred while resetting the password. Please try again later.");
    }
	}

	return (
		<View className='flex-1 bg-gray-100 justify-center px-6'>
			<Text className='text-4xl font-bold text-center text-gray-800 mb-6'>Reset your password</Text>

			{/* Input de New Password */}
			<TextInput className='bg-white p-4 rounded-lg shadow mb-4 text-xl' placeholder='New Password' secureTextEntry value={newPassword} onChangeText={setNewPassword} />

			{/* Input de Confirm Password */}
			<TextInput className='bg-white p-4 rounded-lg shadow mb-6 text-xl' placeholder='Confirm Password' secureTextEntry value={confirmPassword} onChangeText={setConfirmPassword} />

			{/* Botón Reset Password */}
			<TouchableOpacity className='bg-green-500 p-4 rounded-full shadow items-center' onPress={handleResetPassword}>
				<Text className='text-white text-xl font-semibold'>Reset Password</Text>
			</TouchableOpacity>
		</View>
	)
}

export default ResetPassword
