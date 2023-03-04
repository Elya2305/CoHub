package com.company.controller

import com.company.domain.UserProfileRequest
import com.company.domain.UserProfileResponse
import com.company.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/profile")
class UserProfileController(
    private val userService: UserService
) {

    private val log = Logger.getLogger(this.javaClass.name)

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): UserProfileResponse {
        log.info("Request in getting user profile $id")
        val response = userService.getProfile(id)
        log.info("Response in getting user profile $response")
        return response
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody request: UserProfileRequest): UserProfileResponse {
        log.info("Request in updating user profile $id $request")
        val response = userService.updateProfile(id, request)
        log.info("Response in updating user profile $response")
        return response
    }
}
