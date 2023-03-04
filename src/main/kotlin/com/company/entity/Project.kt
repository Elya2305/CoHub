package com.company.entity

import com.company.domain.ShortUserResponse
import com.company.entity.ProjectStatus.OPEN
import com.vladmihalcea.hibernate.type.array.ListArrayType
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@TypeDefs(
    TypeDef(name = "list-array", typeClass = ListArrayType::class),
    TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
)
@Table(name = "projects")
data class Project(
    @Id
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String,
    @Type(type = "list-array")
    @Column(name = "tags", columnDefinition = "text[]", nullable = false)
    var tags: List<String> = emptyList(),
    val resultLink: String? = null,
    val pic: String,
    @Enumerated(EnumType.STRING)
    var status: ProjectStatus = OPEN,
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    val author: User,
    @Type(type = "jsonb")
    @Column(name = "team", columnDefinition = "jsonb", nullable = false)
    val team: ArrayList<ShortUserResponse> = ArrayList(),
)

enum class ProjectStatus {
    OPEN, IN_PROGRESS, FINISHED
}