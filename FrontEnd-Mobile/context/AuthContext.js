import React, { createContext, useContext, useState, useEffect } from 'react'
import AsyncStorage from '@react-native-async-storage/async-storage'

const AuthContext = createContext()

export const AuthProvider = ({ children }) => {
	const [token, setToken] = useState(null)
	const [role, setRole] = useState(null)

	useEffect(() => {
		const loadAuthData = async () => {
			const storedToken = await AsyncStorage.getItem('token')
			const storedRole = await AsyncStorage.getItem('role')
			if (storedToken && storedRole) {
				setToken(storedToken)
				setRole(storedRole)
			}
		}
		loadAuthData()
	}, [])

	const login = (tokenData, roleData) => {
		setToken(tokenData)
		setRole(roleData)
	}

	const logout = async () => {
		setToken(null)
		setRole(null)
		await AsyncStorage.removeItem('token')
		await AsyncStorage.removeItem('role')
	}

	return <AuthContext.Provider value={{ token, role, login, logout }}>{children}</AuthContext.Provider>
}

export const useAuth = () => useContext(AuthContext)
