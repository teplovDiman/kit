package com.life.kit.modules.note_tag

import com.life.kit.common.BaseEntity
import com.life.kit.modules.user_role_permission.user.UserEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = "note", name = "note_tag")
class NoteTag(

  @Column(name = "name")
  var name: String? = null,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by")
  var createdBy: UserEntity? = null,

  @Column(name = "created_at")
  var createdAt: LocalDateTime? = null

): BaseEntity() {

  override fun toString(): String {
    return "${this.javaClass.simpleName}(${super.toString()}, name=$name, createdAt=$createdAt)"
  }
}
