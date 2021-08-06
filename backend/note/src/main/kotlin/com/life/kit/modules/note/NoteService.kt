package com.life.kit.modules.note

import com.life.kit.common.getOneById
import com.life.kit.modules.user_role_permission.user.UserHelper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
open class NoteService(

  private val noteMapper: NoteMapper,
  private val noteRepository: NoteRepository,
  private val userHelper: UserHelper

) {

  open fun save(noteDto: NoteDto): NoteDto {
    val entityForSave = noteMapper.dtoToEntity(noteDto)
    entityForSave.createdBy = userHelper.getCurrentUser()
    entityForSave.createdAt = LocalDateTime.now()
    return noteMapper.entityToDto(noteRepository.save(entityForSave))
  }

  open fun findAll(pageable: Pageable): Page<NoteDto> {
    return noteRepository.findAllByCreatedById(userHelper.getCurrentUserId(), pageable).map(noteMapper::entityToDto)
  }

  open fun findById(noteId: Long): NoteDto {
    return noteMapper.entityToDto(noteRepository.findByIdOrNull(noteId))
  }

  open fun update(noteId: Long, noteDto: NoteDto): NoteDto {
    val noteEntity = noteRepository.getOneById(noteId)
    noteMapper.updateEntityFromDto(noteEntity, noteDto)
    return noteMapper.entityToDto(noteEntity)
  }

  open fun delete(noteId: Long) {
    noteRepository.deleteById(noteId)
  }
}
