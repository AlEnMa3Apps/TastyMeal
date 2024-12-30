import React, { useState, useEffect, useContext } from 'react'
import { View, Text, ActivityIndicator, FlatList, TextInput, Button, Alert } from 'react-native'
import { useLocalSearchParams } from 'expo-router'
import { styled } from 'nativewind'
import api from '../../../api/api'
import { UserContext } from '../../../context/UserContext'

// Ejemplo de componentes "styled" con NativeWind
const StyledView = styled(View)
const StyledText = styled(Text)
const StyledTextInput = styled(TextInput)

// Componente principal
export default function ReportRecipe() {
	const { recipeId } = useLocalSearchParams()
	const { username } = useContext(UserContext)

	// Estado para almacenar los reports de esta receta
	const [reports, setReports] = useState([])
	// Controlar la carga
	const [loading, setLoading] = useState(true)

	// Estados para crear un nuevo report
	const [newReportDescription, setNewReportDescription] = useState('')

	// Estados para editar un report existente
	const [editingReportId, setEditingReportId] = useState(null)
	const [editingReportDescription, setEditingReportDescription] = useState('')

	useEffect(() => {
		if (recipeId) {
			fetchReports()
		}
	}, [recipeId])

	/**
	 * GET /api/recipe/{recipeId}/reports
	 * Obtiene todos los reports de la receta
	 */
	const fetchReports = async () => {
		setLoading(true)
		try {
			const response = await api.get(`/api/recipe/${recipeId}/reports`)
			setReports(response.data) // Asumiendo array de { id, report, author, ... }
		} catch (error) {
			console.error('Error obteniendo reports:', error)
		} finally {
			setLoading(false)
		}
	}

	/**
	 * POST /api/recipe/{recipeId}/report
	 * Crea un nuevo report para esta receta
	 */
	const handleCreateReport = async () => {
		if (!newReportDescription.trim()) {
			Alert.alert('Error', 'Debes completar la descripción del reporte.')
			return
		}

		try {
			const body = { report: newReportDescription }
			await api.post(`/api/recipe/${recipeId}/report`, body)
			setNewReportDescription('')
			fetchReports()
		} catch (error) {
			console.error('Error creando report:', error)
		}
	}

	/**
	 * PUT /api/report/{id}
	 * Edita un report propio
	 */
	const handleEditReport = async () => {
		if (!editingReportDescription.trim()) {
			Alert.alert('Error', 'Debes completar la descripción del reporte.')
			return
		}

		try {
      const body = { report: editingReportDescription }
      await api.put(`/api/report/${editingReportId}`, body)
      cancelEditReport()
      fetchReports()
    } catch (error) {
      // Aquí capturamos el error
      if (error.response && error.response.status === 400) {
        Alert.alert('Error', 'No estás autorizado para editar este reporte.')
      } else {
        console.error('Error editando report:', error)
        Alert.alert('Error', 'Ocurrió un error inesperado al editar el reporte.')
      }
    }
	}

	/**
	 * DELETE /api/report/{id}
	 * Elimina un report propio
	 */
	const handleDeleteReport = async (reportId) => {
		Alert.alert('Confirmar eliminación', '¿Estás seguro que quieres eliminar este report?', [
			{ text: 'Cancelar', style: 'cancel' },
			{
				text: 'Eliminar',
				style: 'destructive',
				onPress: async () => {
          try {
            await api.delete(`/api/report/${reportId}`)
            fetchReports()
          } catch (error) {
            if (error.response && error.response.status === 400) {
              Alert.alert('Error', 'No estás autorizado para eliminar este reporte.')
            } else {
              console.error('Error eliminando report:', error)
              Alert.alert('Error', 'Ocurrió un error inesperado al eliminar el reporte.')
            }
          }
				}
			}
		])
	}

	// Iniciar edición de un report
	const startEditReport = (report) => {
    console.log(report.id)
		setEditingReportId(report.id)
		setEditingReportDescription(report.report)
	}

	// Cancelar la edición
	const cancelEditReport = () => {
		setEditingReportId(null)
		setEditingReportDescription('')
	}

	/**
	 * Renderiza cada "report" en el FlatList
	 */
	const renderReportItem = ({ item }) => {
		// MODO EDICIÓN
		if (editingReportId === item.id) {
			return (
				<StyledView className='mb-3 bg-gray-300 p-3 rounded'>
					<StyledText className='text-base font-bold text-gray-700'>Modo edición:</StyledText>
					<StyledTextInput className='mt-2 bg-white p-2 text-gray-800 rounded' placeholder='Report' value={editingReportDescription} onChangeText={setEditingReportDescription} multiline />
					<StyledView className='flex-row justify-between mt-3'>
						<Button title='Guardar' onPress={handleEditReport} />
						<Button title='Cancelar' onPress={cancelEditReport} />
					</StyledView>
				</StyledView>
			)
		}

		// MODO LECTURA
		return (
			<StyledView className='mb-3 bg-white p-3 rounded'>
				<StyledText className='text-base font-semibold text-gray-800'>{item.report}</StyledText>

				{/* Solo si item.author === username mostramos Editar/Eliminar */}
				{/* {item.author === username && ( */}
					<StyledView className='flex-row justify-evenly mt-2'>
						<Button title='Editar' onPress={() => startEditReport(item)} />
						<Button title='Eliminar' onPress={() => handleDeleteReport(item.id)} />
					</StyledView>
				{/* )} */}
			</StyledView>
		)
	}

	// Mostrar mientras carga
	if (loading) {
		return (
			<StyledView className='flex-1 items-center justify-center bg-gray-200'>
				<ActivityIndicator size='large' />
				<StyledText className='mt-2 text-base text-gray-700'>Cargando reports...</StyledText>
			</StyledView>
		)
	}

	// Layout principal
	return (
		<StyledView className='flex-1 bg-gray-300 p-4'>
			<StyledText className='mb-10 mt-4 text-2xl font-bold text-gray-950 text-center'>Reportes de la receta</StyledText>

			{/* FlatList para mostrar la lista de reports */}
			<FlatList data={reports} keyExtractor={(item) => item.id.toString()} renderItem={renderReportItem} contentContainerStyle={{ paddingBottom: 20 }} />

			{/* Formulario para crear un nuevo reporte */}
			<StyledView className='bg-white p-4 rounded'>
				<StyledText className='text-base font-semibold text-gray-800'>Crear nuevo reporte:</StyledText>
				<StyledTextInput className='mt-2 mb-3 bg-gray-100 p-2 text-gray-800 rounded' placeholder='Descripción del reporte...' value={newReportDescription} onChangeText={setNewReportDescription} multiline />
				<Button title='Crear Report' onPress={handleCreateReport} />
			</StyledView>
		</StyledView>
	)
}
