package com.ef.notetakingapi.services;

import java.util.List;

import com.ef.notetakingapi.dtos.NoteDTO;

public interface INoteService {

	/**
	 * Method used to create a new note on DB
	 * 
	 * @param noteDtoRequest
	 * @return the new note created on DB
	 */
	NoteDTO createNewNote(NoteDTO noteDtoRequest);

	/**
	 * Method used to create a new notes on DB
	 * 
	 * @param notesDtoRequestList
	 * @return the list of new notes created on DB
	 */
	List<NoteDTO> createNewNotes(List<NoteDTO> notesDtoRequestList);

	/**
	 * Method to retrieve a note by ID
	 * 
	 * @param noteId
	 * @return
	 */
	NoteDTO getNoteById(Long noteId);

	/**
	 * Method to retrieve a list of notes by id
	 * 
	 * @param notesIds
	 * @return
	 */
	List<NoteDTO> getNotesByIds(List<Long> notesIds);

	/**
	 * Method to edit a note
	 * 
	 * @param noteDtoRequest
	 * @return
	 */
	NoteDTO editNote(NoteDTO noteDtoRequest);

	/**
	 * Method to edit a list of notes
	 * 
	 * @param notesDtoRequestList
	 * @return
	 */
	List<NoteDTO> editNotes(List<NoteDTO> notesDtoRequestList);

	/**
	 * Method to delete a note by ID
	 * 
	 * @param noteId
	 */
	void deleteNoteById(Long noteId);

	/**
	 * Method to delete a list of notes by id
	 * 
	 * @param notesIds
	 */
	void deleteNotesByIds(List<Long> notesIds);

}