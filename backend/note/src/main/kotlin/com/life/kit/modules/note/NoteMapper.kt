package com.life.kit.modules.note

import com.life.kit.common.BaseMapper
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface NoteMapper : BaseMapper<NoteEntity, NoteDto>
