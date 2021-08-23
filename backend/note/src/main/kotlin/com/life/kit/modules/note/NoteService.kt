package com.life.kit.modules.note

import com.life.kit.common.getOneById
import com.life.kit.modules.user_role_permission.user.UserHelper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated

@Validated
@Transactional
@Service
open class NoteService(

  private val noteMapper: NoteMapper,
  private val noteRepository: NoteRepository,
  private val userHelper: UserHelper

) {

  open fun save(noteDto: NoteDto): GetNoteDto {
    val entityForSave = noteMapper.dtoToEntity(noteDto)
    return noteMapper.entityToDto(noteRepository.save(entityForSave))
  }

  open fun findAll(pageable: Pageable): Page<GetNoteDto> {
    return noteRepository.findAllByCreatedById(userHelper.getCurrentUserId(), pageable).map(noteMapper::entityToDto)
  }

  open fun findById(@NoteNotFound noteId: Long): GetNoteDto {
    return noteMapper.entityToDto(noteRepository.findByIdOrNull(noteId))
  }

  open fun update(@NoteNotFound noteId: Long, noteDto: NoteDto): GetNoteDto {
    val noteEntity = noteRepository.getOneById(noteId)
    noteMapper.updateEntityFromDto(noteEntity, noteDto)
    return noteMapper.entityToDto(noteEntity)
  }

  open fun delete(@NoteNotFound noteId: Long) {
    noteRepository.deleteById(noteId)
  }
}
