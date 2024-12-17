import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { addCommentToRecipe, fetchCommentsForRecipe, editComment, deleteComment } from '../services/commentsService';

import '../css/editComments.css';

/**
 * EditComments component allows managing comments for a specific recipe.
 *
 * This component fetches comments for a recipe identified by its `recipeId`,
 * allows users to add, edit, and delete comments, and navigate back to the recipe editor.
 *
 * @component
 * @returns {JSX.Element}
 * @example
 * // Example usage:
 * <EditComments />
 *
 * @author Enric Nanot Melchor
 */
const EditComments = () => {
    const { recipeId } = useParams(); // Recipe ID from URL parameters
    const navigate = useNavigate(); // Navigation hook to redirect user

    /**
     * State to store the list of comments.
     * @type {Array<Object>}
     */
    const [comments, setComments] = useState([]);

    /**
     * State to store the content of a new comment.
     * @type {string}
     */
    const [newComment, setNewComment] = useState('');

    /**
     * State to track the ID of the comment being edited.
     * @type {number|null}
     */
    const [editCommentId, setEditCommentId] = useState(null);

    /**
     * State to store the updated text for the comment being edited.
     * @type {string}
     */
    const [editCommentText, setEditCommentText] = useState('');

    console.log("ID de la receta:", recipeId);

    /**
     * Fetches the comments for the recipe when the component mounts or `recipeId` changes.
     *
     * @function useEffect
     */
    useEffect(() => {
        const getComments = async () => {
            try {
                const data = await fetchCommentsForRecipe(recipeId);
                console.log("Comentarios obtenidos:", data);
                setComments(data);
            } catch (error) {
                console.error('Error al obtener los comentarios:', error);
            }
        };
        getComments();
    }, [recipeId]);

    /**
     * Adds a new comment to the recipe.
     *
     * @async
     * @function handleAddComment
     */
    const handleAddComment = async () => {
        if (!newComment.trim()) {
            alert('El comentario no puede estar vacío.');
            return;
        }

        try {
            const commentData = { comment: newComment };
            const addedComment = await addCommentToRecipe(recipeId, commentData);
            setComments((prevComments) => [...prevComments, addedComment]);
            setNewComment(''); // Clears the input field
        } catch (error) {
            console.error('Error al agregar el comentario:', error);
        }
    };

    /**
     * Edits an existing comment identified by its ID.
     *
     * @async
     * @function handleEditComment
     * @param {number} id - The ID of the comment being edited.
     */
    const handleEditComment = async (id) => {
        if (!editCommentText.trim()) {
            alert('El comentario no puede estar vacío.');
            return;
        }

        try {
            const commentData = { comment: editCommentText };
            const updatedComment = await editComment(id, commentData);

            // Updates the specific comment in the state
            setComments((prevComments) =>
                prevComments.map((comment) =>
                    comment.id === id ? { ...comment, comment: updatedComment.comment } : comment
                )
            );

            // Clears the editing state
            setEditCommentId(null);
            setEditCommentText('');
        } catch (error) {
            console.error('Error al editar el comentario:', error);
            alert('Hubo un problema al editar el comentario.');
        }
    };

    /**
     * Deletes a comment identified by its ID.
     *
     * @async
     * @function handleDeleteComment
     * @param {number} id - The ID of the comment to delete.
     */
    const handleDeleteComment = async (id) => {
        if (window.confirm('¿Estás seguro de que deseas eliminar este comentario?')) {
            try {
                await deleteComment(id);
                setComments((prevComments) => prevComments.filter((comment) => comment.id !== id));
            } catch (error) {
                console.error('Error al eliminar el comentario:', error);
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
        <div className="comments-container">
            <h1>Editar Comentarios</h1>

            {/* Section to add a new comment */}
            <div className="new-comment">
                <textarea
                    placeholder="Añade un nuevo comentario"
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                />
                <button className="btn btn-add" onClick={handleAddComment}>
                    Agregar Comentario
                </button>
            </div>

            {/* List of comments */}
            <div className="comments-list">
                {comments.map((comment) => (
                    <div key={comment.id} className="comment-item">
                        {editCommentId === comment.id ? (
                            <textarea
                                value={editCommentText}
                                onChange={(e) => setEditCommentText(e.target.value)}
                            />
                        ) : (
                            <p>{comment.comment}</p>
                        )}

                        {/* Actions for editing and deleting comments */}
                        <div className="comment-actions">
                            {editCommentId === comment.id ? (
                                <button
                                    className="btn btn-save"
                                    onClick={() => handleEditComment(comment.id)}
                                >
                                    Guardar
                                </button>
                            ) : (
                                <button
                                    className="btn btn-edit"
                                    onClick={() => {
                                        setEditCommentId(comment.id);
                                        setEditCommentText(comment.comment);
                                    }}
                                >
                                    Editar
                                </button>
                            )}
                            <button
                                className="btn btn-delete"
                                onClick={() => handleDeleteComment(comment.id)}
                            >
                                Eliminar
                            </button>
                        </div>
                    </div>
                ))}
            </div>

            {/* Back button */}
            <button className="btn btn-back" onClick={handleBack}>
                Volver
            </button>
        </div>
    );
};

export default EditComments;
