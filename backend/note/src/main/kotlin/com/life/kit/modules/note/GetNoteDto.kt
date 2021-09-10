package com.life.kit.modules.note

import com.life.kit.common.IdDto

class GetNoteDto(

  override val id: Long?

) : NoteDto(), IdDto
