package com.company.service

import com.company.domain.SocialUser
import com.company.entity.User
import com.company.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createIfNotPresent(socialUser: SocialUser): String {
        userRepository.findByEmail(socialUser.email)?.let { return it.id }
            ?: return userRepository.save(
                User(
                    id = UUID.randomUUID().toString(),
                    email = socialUser.email
                )
            ).id
    }
}
