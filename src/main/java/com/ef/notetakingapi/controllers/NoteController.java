package com.ef.notetakingapi.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ef.notetakingapi.dtos.NoteDTO;
import com.ef.notetakingapi.services.INoteService;

@RestController
@RequestMapping("note")
public class NoteController {
	private static final Logger log = LoggerFactory.getLogger(NoteController.class);

	@Autowired
	private INoteService noteService;

	@PostMapping
	public ResponseEntity<NoteDTO> createNewNote(@RequestBody NoteDTO noteDTORequest) {
		log.info("Requested to create new note");
		return ResponseEntity.ok(noteService.createNewNote(noteDTORequest));
	}

	@PostMapping("/multiple")
	public ResponseEntity<List<NoteDTO>> createNewNotes(@RequestBody List<NoteDTO> notesDTORequestList) {
		log.info("Requested to create new notes");
		return ResponseEntity.ok(noteService.createNewNotes(notesDTORequestList));
	}

	@GetMapping("/single/{noteId}")
	public ResponseEntity<NoteDTO> getNoteById(@PathVariable(required = true) Long noteId) {
		log.info("Requested to retrieve note with ID = {}", noteId);
		return ResponseEntity.ok(noteService.getNoteById(noteId));
	}

	@GetMapping("/multiple")
	public ResponseEntity<List<NoteDTO>> getNotesByIds(@RequestBody List<Long> notesIds) {
		log.info("Requested to retrieve notes with IDs = {}", notesIds);
		return ResponseEntity.ok(noteService.getNotesByIds(notesIds));
	}

	@PutMapping
	public ResponseEntity<NoteDTO> editNote(@RequestBody NoteDTO noteDTORequest) {
		log.info("Requested to edit note with ID = {}", noteDTORequest.getId());
		return ResponseEntity.ok(noteService.editNote(noteDTORequest));
	}

	@PutMapping("/multiple")
	public ResponseEntity<List<NoteDTO>> editNotes(@RequestBody List<NoteDTO> notesDTORequestList) {
		log.info("Requested to edit notes ");
		return ResponseEntity.ok(noteService.editNotes(notesDTORequestList));
	}

	@DeleteMapping("single/{noteId}")
	public void deleteNoteById(@PathVariable(required = true) Long noteId) {
		log.info("Requested to delete note with ID = {}", noteId);
		noteService.deleteNoteById(noteId);
	}

	@DeleteMapping("/multiple")
	public void deleteNoteById(@RequestBody List<Long> notesIds) {
		log.info("Requested to delete notes ");
		noteService.deleteNotesByIds(notesIds);
	}
}
