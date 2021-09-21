@file:Suppress("CommentSpacing")
package com.life.kit.modules.note

import com.life.kit.common.KitHelper
import com.life.kit.modules.userRolePermission.user.UserHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

@ExtendWith(MockitoExtension::class)
class NoteServiceTest {

  private val userHelper2222222 = UserHelper()
  private val kitHelper = KitHelper()

  @Mock
  lateinit var noteMapper: NoteMapper

  @Mock
  lateinit var noteRepository: NoteRepository

  @Mock
  lateinit var userHelper: UserHelper

  @InjectMocks
  lateinit var noteService: NoteService

  @Test
  fun save() {
    val noteTitle = "Test title"
    val noteValue = "Test value"
    val noteId: Long = 1
    val noteDto = NoteDto(noteTitle, noteValue)
    val noteEntity = NoteEntity(noteTitle, noteValue, userHelper2222222.getCurrentUser(), kitHelper.getLocalDateTimeNow())
    val savedNoteEntity = NoteEntity(noteTitle, noteValue, userHelper2222222.getCurrentUser(), kitHelper.getLocalDateTimeNow())
    savedNoteEntity.id = noteId
    val expected = GetNoteDto(noteId)
    expected.title = noteTitle
    expected.value = noteValue

    `when`(noteMapper.dtoToEntity(noteDto)).thenReturn(noteEntity)
    `when`(noteRepository.save(noteEntity)).thenReturn(savedNoteEntity)
    `when`(noteMapper.entityToDto(savedNoteEntity)).thenReturn(expected)

    val result = noteService.save(noteDto)

    assertEquals(expected, result)

    verify(noteMapper).dtoToEntity(noteDto)
    verify(noteRepository).save(noteEntity)
    verify(noteMapper).entityToDto(savedNoteEntity)
    verifyNoMoreInteractions(noteMapper, noteRepository, noteMapper)
  }
}
