package com.ef.notetakingapi.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ef.notetakingapi.dtos.NoteDTO;
import com.ef.notetakingapi.entities.NoteEntity;

@Converter
@Service
public class NoteConverter implements AttributeConverter<NoteDTO, NoteEntity> {

	private static final Logger log = LoggerFactory.getLogger(NoteConverter.class);
	private static final String DD_MM_YYYY = "dd-MM-yyyy";

	@Override
	public NoteEntity convertToDatabaseColumn(NoteDTO dto) {
		var entity = new NoteEntity();
		log.info("Copying properties form NOTE dto with ID = {} and TITLE = {} to entity", dto.getId(), dto.getTitle());
		BeanUtils.copyProperties(dto, entity);
		var simpleDateFormat = new SimpleDateFormat(DD_MM_YYYY);
		try {
			if (dto.getCreationDate() != null) {
				entity.setCreationDate(simpleDateFormat.parse(dto.getCreationDate()));
			}
			if (dto.getLastUpdateDate() != null) {
				entity.setLastUpdateDate((simpleDateFormat.parse(dto.getLastUpdateDate())));
			}
		} catch (ParseException e) {
			log.error("An error occured while the application was trying to parse date {}", e.getMessage(), e);
		}
		return entity;
	}

	@Override
	public NoteDTO convertToEntityAttribute(NoteEntity entity) {
		var dto = new NoteDTO();
		log.info("Copying properties form NOTE entity with  ID = {} and TITLE = {} to dto", entity.getId(),
				entity.getTitle());
		BeanUtils.copyProperties(entity, dto);
		if (entity.getCreationDate() != null) {
			dto.setCreationDate(entity.getCreationDate().toString());
		}
		if (entity.getLastUpdateDate() != null) {
			dto.setLastUpdateDate(entity.getLastUpdateDate().toString());
		}
		return dto;
	}

	public List<NoteDTO> convertAllToEntityAttribute(List<NoteEntity> entities) {
		List<NoteDTO> noteDTOList = new ArrayList<>();
		if (entities != null && !entities.isEmpty()) {
			for (NoteEntity noteEntity : entities) {
				noteDTOList.add(convertToEntityAttribute(noteEntity));
			}
		}
		return noteDTOList;
	}

}
