// region Suppresses
@file:Suppress("unused")
// endregion

package com.life.kit.modules.noteTag

import com.life.kit.common.BaseEntity
import com.life.kit.config.NoteDatabaseConfig
import com.life.kit.modules.userRolePermission.user.UserEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = NoteDatabaseConfig.SCHEMA_NAME, name = "note_tag")
class NoteTagEntity(

  @Column(name = "name")
  var name: String? = null,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by")
  var createdBy: UserEntity? = null,

  @Column(name = "created_at")
  var createdAt: LocalDateTime? = null

) : BaseEntity() {

  override fun toString(): String {
    return "${this.javaClass.simpleName}(${super.toString()}, name=$name, createdAt=$createdAt)"
  }
}
