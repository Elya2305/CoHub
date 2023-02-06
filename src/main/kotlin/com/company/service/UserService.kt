package com.company.service

import com.company.domain.SocialUser
import com.company.entity.User
import com.company.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createIfNotPresent(socialUser: SocialUser): String {
        val user = userRepository.findByEmail(socialUser.email)
        if (user != null) {
            return user.id
        }
        return userRepository.save(User(email = socialUser.email)).id
    }
}
