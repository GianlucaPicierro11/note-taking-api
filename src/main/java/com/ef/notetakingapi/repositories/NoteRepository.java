package com.ef.notetakingapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ef.notetakingapi.entities.NoteEntity;

@Repository
public interface NoteRepository extends CrudRepository<NoteEntity, Long> {

}
