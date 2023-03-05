package com.company.controller

import com.company.domain.UserContext
import com.company.domain.UserProfileRequest
import com.company.domain.UserProfileResponse
import com.company.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/profiles")
class UserProfileController(
    private val userService: UserService
) {

    private val log = Logger.getLogger(this.javaClass.name)

    @GetMapping("/current")
    fun getCurrent(): UserProfileResponse {
        log.info("Request on getting user profile ${UserContext.getUserUuid()!!}")
        val response = userService.getProfile(UserContext.getUserUuid()!!)
        log.info("Response on getting user profile $response")
        return response
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): UserProfileResponse {
        log.info("Request on getting user profile $id")
        val response = userService.getProfile(id)
        log.info("Response on getting user profile $response")
        return response
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody request: UserProfileRequest): UserProfileResponse {
        log.info("Request on updating user profile $id $request")
        val response = userService.updateProfile(id, request)
        log.info("Response on updating user profile $response")
        return response
    }

    @GetMapping
    fun all(
        @RequestParam(value = "skills", required = false) skills: List<String>?
    ): List<UserProfileResponse> {
        log.info("Request on getting all profiles $skills")
        return userService.getAllProfiles(skills)
    }
}
