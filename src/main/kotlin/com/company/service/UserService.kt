package com.company.service

import com.company.domain.SocialUser
import com.company.domain.UserContext
import com.company.domain.UserProfileRequest
import com.company.domain.UserProfileResponse
import com.company.entity.User
import com.company.exception.EntityNotFoundException
import com.company.exception.ForbiddenActionException
import com.company.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    private var AVATARS = listOf(
        "https://i.ibb.co/cYvhtqf/rabbit.png",
        "https://i.ibb.co/6gvmYzn/penguin.png",
        "https://i.ibb.co/LPbhXcL/owl.png",
        "https://i.ibb.co/f2hFzsx/black-panther.png",
        "https://i.ibb.co/CBS7fGp/whale.png",
        "https://i.ibb.co/N1cJVZs/lion.png",
        "https://i.ibb.co/qYrkSf1/crocodile.png",
        "https://i.ibb.co/r21jVLn/gorilla.png",
        "https://i.ibb.co/7zBLWMb/fox.png"
    )

    fun createIfNotPresent(socialUser: SocialUser): String {
        val user = userRepository.findByEmail(socialUser.email)
        if (user != null) {
            return user.id
        }
        return userRepository.save(newUser(socialUser)).id
    }

    fun getCurrentReference(): User {
        return userRepository.getReferenceById(UserContext.getUserUuid())
    }

    fun get(userId: String): User {
        return getFromDb(userId)
    }

    fun getProfile(id: String): UserProfileResponse {
        val user = getFromDb(id)
        return map(user)
    }

    fun updateProfile(id: String, request: UserProfileRequest): UserProfileResponse {
        if (id != UserContext.getUserUuid()) {
            throw ForbiddenActionException("User $id is not authorized to perform action")
        }

        val user = getFromDb(id)
        user.username = request.username
        user.description = request.description
        user.githubLink = request.githubLink
        user.linkedinLink = request.linkedinLink
        user.skills = request.skills
        user.jobTitle = request.jobTitle

        return map(userRepository.save(user))
    }

    private fun getFromDb(id: String) = userRepository.findById(id).orElseThrow { EntityNotFoundException(id) }

    private fun map(user: User): UserProfileResponse {
        return UserProfileResponse(
            id = user.id,
            username = user.username,
            pic = user.pic,
            jobTitle = user.jobTitle,
            skills = user.skills,
            description = user.description,
            linkedinLink = user.linkedinLink,
            githubLink = user.githubLink
        )
    }

    private fun newUser(socialUser: SocialUser): User {
        val pic = AVATARS.random()
        return User(email = socialUser.email, username = socialUser.username, pic = pic)
    }
}
