import React, { useState, useEffect } from 'react'
import { View, Text, ActivityIndicator, FlatList, TextInput, Button, Alert } from 'react-native'
import { useLocalSearchParams } from 'expo-router'
import api from '../../../api/api' // Ajusta la ruta a tu archivo 'api'

export default function ReportRecipe() {
  const { recipeId } = useLocalSearchParams()

  // Estado para almacenar los reports de esta receta
  const [reports, setReports] = useState([])
  // Controlar la carga
  const [loading, setLoading] = useState(true)

  // Estados para crear un nuevo report
  const [newReportTitle, setNewReportTitle] = useState('')
  const [newReportDescription, setNewReportDescription] = useState('')

  // Estados para editar un report existente
  const [editingReportId, setEditingReportId] = useState(null)
  const [editingReportTitle, setEditingReportTitle] = useState('')
  const [editingReportDescription, setEditingReportDescription] = useState('')

  // Cargar los reports al montar o cuando cambie recipeId
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
      // Asumiendo que `response.data` es un array de reports
      setReports(response.data)
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
    if (!newReportTitle.trim() || !newReportDescription.trim()) {
      Alert.alert('Error', 'Debes completar los campos de título y descripción')
      return
    }

    try {
      const body = {
        title: newReportTitle,
        description: newReportDescription
      }
      await api.post(`/api/recipe/${recipeId}/report`, body)
      // Limpiamos el formulario
      setNewReportTitle('')
      setNewReportDescription('')
      // Refrescamos la lista
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
    if (!editingReportTitle.trim() || !editingReportDescription.trim()) {
      Alert.alert('Error', 'Debes completar los campos de título y descripción')
      return
    }

    try {
      const body = {
        title: editingReportTitle,
        description: editingReportDescription
      }
      await api.put(`/api/report/${editingReportId}`, body)
      // Limpiamos estado de edición
      cancelEditReport()
      // Refrescamos la lista
      fetchReports()
    } catch (error) {
      console.error('Error editando report:', error)
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
            fetchReports() // Recargar la lista
          } catch (error) {
            console.error('Error eliminando report:', error)
          }
        }
      }
    ])
  }

  // Iniciar edición de un report
  const startEditReport = (report) => {
    setEditingReportId(report.id)
    setEditingReportTitle(report.title)
    setEditingReportDescription(report.description)
  }

  // Cancelar la edición
  const cancelEditReport = () => {
    setEditingReportId(null)
    setEditingReportTitle('')
    setEditingReportDescription('')
  }

  // Render de cada report en la FlatList
  const renderReportItem = ({ item }) => {
    // Si el report está en edición
    if (editingReportId === item.id) {
      return (
        <View style={{ backgroundColor: '#ccc', marginBottom: 10, padding: 10 }}>
          <Text>Modo edición:</Text>
          <TextInput
            placeholder="Título"
            value={editingReportTitle}
            onChangeText={setEditingReportTitle}
            style={{ backgroundColor: '#fff', marginVertical: 5, padding: 8 }}
          />
          <TextInput
            placeholder="Descripción"
            value={editingReportDescription}
            onChangeText={setEditingReportDescription}
            multiline
            style={{ backgroundColor: '#fff', marginVertical: 5, padding: 8 }}
          />
          <View style={{ flexDirection: 'row', justifyContent: 'space-between' }}>
            <Button title="Guardar" onPress={handleEditReport} />
            <Button title="Cancelar" onPress={cancelEditReport} />
          </View>
        </View>
      )
    }

    // Modo lectura normal
    return (
      <View style={{ backgroundColor: '#fff', marginBottom: 10, padding: 10 }}>
        <Text className='font-bold'>{item.report}</Text>
        <View style={{ flexDirection: 'row', justifyContent: 'flex-end', marginTop: 5 }}>
          <Button title="Editar" onPress={() => startEditReport(item)} />
          <Button title="Eliminar" onPress={() => handleDeleteReport(item.id)} />
        </View>
      </View>
    )
  }

  // Mostrar mientras carga
  if (loading) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <ActivityIndicator size="large" />
        <Text>Cargando reports...</Text>
      </View>
    )
  }

  return (
    <View style={{ flex: 1, padding: 10, backgroundColor: '#eee' }}>
      <Text style={{ fontWeight: 'bold', fontSize: 18, marginBottom: 10 }}>
        Reports de la receta con ID: {recipeId}
      </Text>

      {/* FlatList para mostrar la lista de reports */}
      <FlatList
        data={reports}
        keyExtractor={(item) => item.id.toString()}
        renderItem={renderReportItem}
        contentContainerStyle={{ paddingBottom: 20 }}
      />

      {/* Formulario para crear un nuevo report */}
      <View style={{ backgroundColor: '#fff', padding: 10, borderRadius: 8 }}>
        <Text style={{ fontWeight: 'bold' }}>Crear nuevo report:</Text>
        <TextInput
          placeholder="Título del report"
          value={newReportTitle}
          onChangeText={setNewReportTitle}
          style={{ backgroundColor: '#f9f9f9', marginVertical: 5, padding: 8 }}
        />
        <TextInput
          placeholder="Descripción"
          value={newReportDescription}
          onChangeText={setNewReportDescription}
          multiline
          style={{ backgroundColor: '#f9f9f9', marginVertical: 5, padding: 8 }}
        />
        <Button title="Crear Report" onPress={handleCreateReport} />
      </View>
    </View>
  )
}