package com.company.entity

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "requests")
data class Request(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    val rejectionReason: String,
    @Enumerated(EnumType.STRING)
    val status: RequestStatus,
)

enum class RequestStatus {
    PENDING, ACCEPTED, REJECTED
}

