package com.company.service

import com.company.domain.TagResponse
import com.company.entity.Tag
import com.company.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepository: TagRepository
) {
    fun all(): List<TagResponse> {
        return tagRepository.findAll()
            .map { TagResponse(it.id, it.title) }
    }

    fun save(tags: List<String>) {
        tags.forEach{ tag ->
            if (tagRepository.findByTitle(tag) == null) {
                tagRepository.save(Tag(title = tag))
            }
        }
    }
}
