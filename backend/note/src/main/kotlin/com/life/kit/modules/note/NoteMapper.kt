package com.life.kit.modules.note

import com.life.kit.modules.userRolePermission.common.BaseMapper
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class NoteMapper : BaseMapper<NoteEntity, NoteDto, GetNoteDto>()
