package com.life.kit.modules.note

import com.life.kit.common.BaseSearchParam
import com.life.kit.config.exception.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springdoc.api.annotations.ParameterObject
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/notes")
class NoteController(

  private val noteService: NoteService

) {

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  fun create(@Valid @RequestBody noteDto: NoteDto): GetNoteDto {
    return noteService.save(noteDto)
  }

//  @PageableAsQueryParam
//  @GetMapping
//  fun findAll(@Parameter(hidden = true) pageable: Pageable): Page<GetNoteDto> {
//    return noteService.findAll(pageable)
//  }

  @Operation(summary = "Get notes")
  @GetMapping
  fun findAll(@ParameterObject baseSearchParam: BaseSearchParam): Page<GetNoteDto> {
    return noteService.findAll(baseSearchParam)
  }

  // region REST API Docs
  @Operation(summary = "Get the note by ID")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "Successfully retrieved note", content = [(Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = GetNoteDto::class)))))]),
      ApiResponse(responseCode = "400", description = "Missing or invalid note id", content = [(Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class)))))]),
      ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = [(Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class)))))]),
      ApiResponse(responseCode = "500", description = "Internal Server Error", content = [(Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class)))))])
    ]
  )
  // endregion
  @GetMapping("/{noteId}")
  fun findById(@Parameter(description = "id of note to be searched") @PathVariable noteId: Long): GetNoteDto {
    return noteService.findById(noteId)
  }

  @PutMapping("/{noteId}")
  fun update(@PathVariable noteId: Long,
             @Valid @RequestBody noteDto: NoteDto): GetNoteDto {
    return noteService.update(noteId, noteDto)
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{noteId}")
  fun delete(@PathVariable noteId: Long) {
    return noteService.delete(noteId)
  }
}
