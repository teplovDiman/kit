package com.life.kit.modules.profile

import com.life.kit.modules.BaseEntity
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(schema = "users", name = "profile")
class ProfileEntity : BaseEntity() {

    @Column(name = "[type]", nullable = false)
    @Enumerated
    var type: ProfileType? = null

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "middle_name")
    var middleName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    @Column(name = "is_man")
    var isMan: Boolean? = null

    @Column(name = "birth_date")
    var birthDate: LocalDate? = null
}
