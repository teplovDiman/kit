// region Suppresses
@file:Suppress("unused")
// endregion

package com.life.kit.modules.note

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
@Constraint(validatedBy = [NoteNotFoundValidator::class])
annotation class NoteNotFound(

  val message: String = "{kit.note.NoteNotFound}",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = []

)

class NoteNotFoundValidator(

  private val noteRepository: NoteRepository

) : ConstraintValidator<NoteNotFound, Long> {

  override fun isValid(noteId: Long, context: ConstraintValidatorContext): Boolean = noteRepository.findById(noteId).isPresent
}
