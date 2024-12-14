import React, { createContext, useState } from 'react'

// Creamos el contexto
export const UserContext = createContext()

// Componente proveedor que envolverá la app
export const UserProvider = ({ children }) => {
	const [username, setUsername] = useState('')

	return <UserContext.Provider value={{ username, setUsername }}>{children}</UserContext.Provider>
}
