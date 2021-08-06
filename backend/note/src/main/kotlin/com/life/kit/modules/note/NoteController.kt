package com.life.kit.modules.note

import org.springframework.data.domain.Page
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

@RestController
@RequestMapping("/api/notes")
class NoteController(

  private val noteService: NoteService

) {

  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping
  fun create(@RequestBody noteDto: NoteDto): NoteDto {
    return noteService.save(noteDto)
  }

  @GetMapping
  fun findAll(pageable: Pageable): Page<NoteDto> {
    return noteService.findAll(pageable)
  }

  @GetMapping("/{noteId}")
  fun findById(@PathVariable noteId: Long): NoteDto {
    return noteService.findById(noteId)
  }

  @PutMapping("/{noteId}")
  fun update(@PathVariable noteId: Long, @RequestBody noteDto: NoteDto): NoteDto {
    return noteService.update(noteId, noteDto)
  }

  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{noteId}")
  fun delete(@PathVariable noteId: Long) {
    return noteService.delete(noteId)
  }
}
