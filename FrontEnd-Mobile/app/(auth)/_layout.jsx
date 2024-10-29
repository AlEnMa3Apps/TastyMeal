/**
 * @file AuthLayout.jsx
 * @description Configura la estructura de autenticación de la aplicación, incluyendo las pantallas de login y signUp.
 * @author Manuel García Nieto
 */

import React from 'react'
import { Stack } from 'expo-router'
import { useEffect } from 'react'
import { setStatusBarStyle } from 'expo-status-bar'

/**
 * @component
 * @name AuthLayout
 * @description
 * Este componente configura la estructura de autenticación de la aplicación utilizando
 * el `Stack` de `expo-router`. Además, ajusta el estilo de la barra de estado al montar
 * el componente.
 *
 * @returns {JSX.Element} El layout de autenticación que incluye las pantallas de login y signUp.
 */
const AuthLayout = () => {
	/**
	 * @function
	 * @name useEffect
	 * @description
	 * Este hook se ejecuta una vez al montar el componente. Utiliza `setTimeout` para
	 * cambiar el estilo de la barra de estado a 'dark' inmediatamente después de la
	 * renderización inicial.
	 */
	useEffect(() => {
		setTimeout(() => {
			setStatusBarStyle('dark')
		}, 0)
	}, [])
	return (
		<>
			<Stack>
				 {/**
         * @screen
         * @name login
         * @description
         * Define la pantalla de inicio de sesión (`login`). La opción `headerShown: false`
         * oculta la barra de encabezado para esta pantalla.
         */}
				<Stack.Screen name='login' options={{ headerShown: false }} />
				{/**
				 * @screen
				 * @name signUp
				 * @description
				 * Define la pantalla de registro (`signUp`). Al igual que la pantalla de login,
				 * la barra de encabezado está oculta.
				 */}
				<Stack.Screen name='signUp' options={{ headerShown: false }} />
			</Stack>
		</>
	)
}

export default AuthLayout
