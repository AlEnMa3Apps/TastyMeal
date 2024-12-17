import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { addReportToRecipe, fetchReportsForRecipe, editReport, deleteReport } from '../services/reportServices';

import '../css/editReports.css';

/**
 * EditReports component allows managing reports for a specific recipe.
 *
 * This component fetches reports for a recipe identified by its `recipeId`,
 * allows users to add, edit, and delete reports, and navigate back to the recipe editor.
 *
 * @component
 * @returns {JSX.Element}
 * @example
 * // Example usage:
 * <EditReports />
 *
 * @author Enric Nanot Melchor
 */
const EditReports = () => {
    const { recipeId } = useParams(); // Recipe ID from URL parameters
    const navigate = useNavigate(); // Navigation hook to redirect user

    /**
     * State to store the list of reports.
     * @type {Array<Object>}
     */
    const [reports, setReports] = useState([]);

    /**
     * State to store the content of a new report.
     * @type {string}
     */
    const [newReport, setNewReport] = useState('');

    /**
     * State to track the ID of the report being edited.
     * @type {number|null}
     */
    const [editReportId, setEditReportId] = useState(null);

    /**
     * State to store the updated text for the report being edited.
     * @type {string}
     */
    const [editReportText, setEditReportText] = useState('');

    console.log("ID de la receta:", recipeId);

    /**
     * Fetches the reports for the recipe when the component mounts or `recipeId` changes.
     *
     * @function useEffect
     */
    useEffect(() => {
        const getReports = async () => {
            try {
                const data = await fetchReportsForRecipe(recipeId);
                console.log("Reports obtenidos:", data);
                setReports(data);
            } catch (error) {
                console.error('Error al obtener los reports:', error);
            }
        };
        getReports();
    }, [recipeId]);

    /**
     * Adds a new report to the recipe.
     *
     * @async
     * @function handleAddReport
     */
    const handleAddReport = async () => {
        if (!newReport.trim()) {
            alert('El reporte no puede estar vacío.');
            return;
        }

        try {
            const reportData = { report: newReport };
            const addedReport = await addReportToRecipe(recipeId, reportData);
            setReports((prevReports) => [...prevReports, addedReport]);
            setNewReport(''); // Clears the input field
        } catch (error) {
            console.error('Error al agregar el reporte:', error);
        }
    };

    /**
     * Edits an existing report identified by its ID.
     *
     * @async
     * @function handleEditReport
     * @param {number} id - The ID of the report being edited.
     */
    const handleEditReport = async (id) => {
        if (!editReportText.trim()) {
            alert('El reporte no puede estar vacío.');
            return;
        }

        try {
            const reportData = { report: editReportText };
            const updatedReport = await editReport(id, reportData);

            // Updates the specific report in the state
            setReports((prevReports) =>
                prevReports.map((report) =>
                    report.id === id ? { ...report, report: updatedReport.report } : report
                )
            );

            // Clears the editing state
            setEditReportId(null);
            setEditReportText('');
        } catch (error) {
            console.error('Error al editar el reporte:', error);
            alert('Hubo un problema al editar el reporte.');
        }
    };

    /**
     * Deletes a report identified by its ID.
     *
     * @async
     * @function handleDeleteReport
     * @param {number} id - The ID of the report to delete.
     */
    const handleDeleteReport = async (id) => {
        if (window.confirm('¿Estás seguro de que deseas eliminar este reporte?')) {
            try {
                await deleteReport(id);
                setReports((prevReports) => prevReports.filter((report) => report.id !== id));
            } catch (error) {
                console.error('Error al eliminar el reporte:', error);
            }
        }
    };

    /**
     * Navigates back to the recipe editing page.
     *
     * @function handleBack
     */
    const handleBack = () => {
        navigate(`/editRecipe/${recipeId}`);
    };

    return (
        <div className="reports-container">
            <h1>Editar Reportes</h1>


            <div className="new-report">
                <textarea
                    placeholder="Añade un nuevo reporte"
                    value={newReport}
                    onChange={(e) => setNewReport(e.target.value)}
                />
                <button className="btn btn-add" onClick={handleAddReport}>
                    Agregar Reporte
                </button>
            </div>


            <div className="reports-list">
                {reports.map((report) => (
                    <div key={report.id} className="report-item">
                        {editReportId === report.id ? (
                            <textarea
                                value={editReportText}
                                onChange={(e) => setEditReportText(e.target.value)}
                            />
                        ) : (
                            <p>{report.report}</p>
                        )}


                        <div className="report-actions">
                            {editReportId === report.id ? (
                                <button
                                    className="btn btn-save"
                                    onClick={() => handleEditReport(report.id)}
                                >
                                    Guardar
                                </button>
                            ) : (
                                <button
                                    className="btn btn-edit"
                                    onClick={() => {
                                        setEditReportId(report.id);
                                        setEditReportText(report.report);
                                    }}
                                >
                                    Editar
                                </button>
                            )}
                            <button
                                className="btn btn-delete"
                                onClick={() => handleDeleteReport(report.id)}
                            >
                                Eliminar
                            </button>
                        </div>
                    </div>
                ))}
            </div>
            <button className="btn btn-back" onClick={handleBack}>
                Volver
            </button>
        </div>
    );
};

export default EditReports;
