package com.ef.notetakingapi.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.notetakingapi.converters.NoteConverter;
import com.ef.notetakingapi.dtos.NoteDTO;
import com.ef.notetakingapi.entities.NoteEntity;
import com.ef.notetakingapi.exceptions.NoteException;
import com.ef.notetakingapi.repositories.NoteRepository;

@Service
public class NoteServiceImpl implements INoteService {

	private static final Logger log = LoggerFactory.getLogger(NoteServiceImpl.class);

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private NoteConverter noteConverter;

	/**
	 * Method used to create a new note on DB
	 * 
	 * @param noteDtoRequest
	 * @return the new note created on DB
	 */
	@Override
	public NoteDTO createNewNote(NoteDTO noteDtoRequest) {
		try {
			log.info("Starting to create new note from NOTE DTO request with title = {}", noteDtoRequest.getTitle());
			NoteEntity nodeToSave = noteConverter.convertToDatabaseColumn(noteDtoRequest);
			nodeToSave.setCreationDate(new Date());
			nodeToSave.setLastUpdateDate(new Date());
			NoteEntity nodeSaved = noteRepository.save(nodeToSave);
			log.info("New note created with ID = {}", noteDtoRequest.getId());
			return noteConverter.convertToEntityAttribute(nodeSaved);
		} catch (Exception e) {
			log.error("Fail to create new note: {}", e.getMessage(), e);
			throw new NoteException("Fail to create new note: " + e.getMessage(), e);
		}
	}

	/**
	 * Method used to create a new notes on DB
	 * 
	 * @param notesDtoRequestList
	 * @return the list of new notes created on DB
	 */
	@Override
	public List<NoteDTO> createNewNotes(List<NoteDTO> notesDtoRequestList) {
		List<NoteDTO> result = new ArrayList<>();
		for (NoteDTO noteDTO : notesDtoRequestList) {
			result.add(this.createNewNote(noteDTO));
		}
		return result;
	}

	/**
	 * Method to retrieve a note by ID
	 * 
	 * @param noteId
	 * @return
	 */
	@Override
	public NoteDTO getNoteById(Long noteId) {
		log.info("Starting to find note with ID = {}", noteId);
		return noteConverter.convertToEntityAttribute(
				noteRepository.findById(noteId).orElseThrow(() -> new NoteException("Note not found")));

	}

	/**
	 * Method to retrieve a list of notes by id
	 * 
	 * @param notesIds
	 * @return
	 */
	@Override
	public List<NoteDTO> getNotesByIds(List<Long> notesIds) {
		List<NoteDTO> result = new ArrayList<>();
		for (Long noteId : notesIds) {
			result.add(this.getNoteById(noteId));

		}
		log.info("Found {} notes", result);
		return result;
	}

	/**
	 * Method to edit a note
	 * 
	 * @param noteDtoRequest
	 * @return
	 */
	@Override
	public NoteDTO editNote(NoteDTO noteDtoRequest) {
		try {
			log.info("Starting to edit note with ID = {}", noteDtoRequest.getId());
			NoteEntity nodeToEdit = noteRepository.findById(noteDtoRequest.getId())
					.orElseThrow(() -> new NoteException("Note not found"));
			nodeToEdit.setDescription(noteDtoRequest.getDescription());
			nodeToEdit.setLastUpdateDate(new Date());
			nodeToEdit.setNote(noteDtoRequest.getNote());
			nodeToEdit.setTitle(noteDtoRequest.getTitle());
			NoteEntity nodeEdited = noteRepository.save(nodeToEdit);
			log.info("Note with ID = {} edited", noteDtoRequest.getId());
			return noteConverter.convertToEntityAttribute(nodeEdited);
		} catch (Exception e) {
			log.error("Fail to edit note: {}", e.getMessage(), e);
			throw new NoteException("Fail to edit note: " + e.getMessage(), e);
		}
	}

	/**
	 * Method to edit a list of notes
	 * 
	 * @param notesDtoRequestList
	 * @return
	 */
	@Override
	public List<NoteDTO> editNotes(List<NoteDTO> notesDtoRequestList) {
		List<NoteDTO> result = new ArrayList<>();
		for (NoteDTO noteDTO : notesDtoRequestList) {
			result.add(this.editNote(noteDTO));
		}
		return result;
	}

	/**
	 * Method to delete a note by ID
	 * 
	 * @param noteId
	 */
	@Override
	public void deleteNoteById(Long noteId) {
		try {
			log.info("Starting to delete note with ID = {}", noteId);
			NoteEntity nodeToDelete = noteRepository.findById(noteId)
					.orElseThrow(() -> new NoteException("Note not found"));
			noteRepository.delete(nodeToDelete);
			log.info("Note with ID = {} deleted", noteId);
		} catch (Exception e) {
			log.error("Fail to edit note: {}", e.getMessage(), e);
			throw new NoteException("Fail to edit note: " + e.getMessage(), e);
		}
	}

	/**
	 * Method to delete a list of notes by id
	 * 
	 * @param notesIds
	 */
	@Override
	public void deleteNotesByIds(List<Long> notesIds) {
		for (Long noteId : notesIds) {
			this.deleteNoteById(noteId);
		}
	}

}
