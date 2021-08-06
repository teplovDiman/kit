package com.life.kit.modules.note

import com.life.kit.common.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface NoteRepository : Repository<NoteEntity> {

  fun findAllByCreatedById(id: Long, pageable: Pageable): Page<NoteEntity>
}
