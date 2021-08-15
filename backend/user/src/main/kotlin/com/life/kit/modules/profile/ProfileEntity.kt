package com.life.kit.modules.profile

import com.life.kit.common.BaseEntity
import com.life.kit.config.UserDatabaseConfig
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(schema = UserDatabaseConfig.SCHEMA_NAME, name = "profile")
class ProfileEntity(

  @Enumerated
  @Column(name = "[type]", nullable = false)
  var type: ProfileType? = null,

  @Column(name = "first_name")
  var firstName: String? = null,

  @Column(name = "middle_name")
  var middleName: String? = null,

  @Column(name = "last_name")
  var lastName: String? = null,

  @Column(name = "is_man")
  var isMan: Boolean? = null,

  @Column(name = "birth_date")
  var birthDate: LocalDate? = null

) : BaseEntity() {

  override fun toString(): String {
    return "${this.javaClass.simpleName}(${super.toString()}, type=$type, firstName=$firstName, middleName=$middleName, lastName=$lastName, " +
        "isMan=$isMan, birthDate=$birthDate)"
  }

}
