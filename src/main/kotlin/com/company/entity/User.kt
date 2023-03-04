package com.company.entity

import com.vladmihalcea.hibernate.type.array.ListArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@TypeDefs(
    TypeDef(name = "list-array", typeClass = ListArrayType::class),
)
@Table(name = "users")
class User(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    @Column(name = "pic", columnDefinition = "text")
    val pic: String? = null,
    @Column(unique = true)
    var username: String,
    @Type(type = "list-array")
    @Column(name = "skills", columnDefinition = "text[]", nullable = false)
    var skills: ArrayList<String> = ArrayList(),
    var linkedinLink: String? = null,
    var githubLink: String? = null,
    var jobTitle: String? = null,
    @Column(name = "description", columnDefinition = "text")
    var description: String? = null,
)
