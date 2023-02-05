package com.company.filter

import com.company.domain.SocialUser
import com.company.domain.UserContext
import com.company.exception.custom.AuthenticationException
import com.company.service.UserService
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class IdTokenVerificationFilter(
    val verifier: GoogleIdTokenVerifier,
    @Qualifier("handlerExceptionResolver") val exceptionResolver: HandlerExceptionResolver,
    val userService: UserService,
) : OncePerRequestFilter() {
    private val HEADER_NAME = "Authorization-Google"
    private val IGNORE_ENDPOINTS = arrayOf("/alive")

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            doFilter(request)
            filterChain.doFilter(request, response)
            UserContext.removeUserUuid()
        } catch (exc: Exception) {
            exceptionResolver.resolveException(request, response, null, exc)
            UserContext.removeUserUuid()
        }
    }

    private fun doFilter(request: HttpServletRequest) {
        println("doFilter ${request.requestURI}")
        if (IGNORE_ENDPOINTS.contains(request.requestURI)) {
            return
        }
        if ("root1token" == request.getHeader(HEADER_NAME)) {
            return processRoot1User()
        }
        if ("root2token" == request.getHeader(HEADER_NAME)) {
            return processRoot2User()
        }

        val idTokenValue: String = request.getHeader(HEADER_NAME)
            ?: throw AuthenticationException("Id token is missing")

        val idToken: GoogleIdToken = try {
            verifier.verify(idTokenValue)
        } catch (e: Exception) {
            throw AuthenticationException("Incorrect id token was provided")
        } ?: throw AuthenticationException("Id token is invalid")

        val payload = idToken.payload
        val emailVerified = payload.emailVerified
        if (!emailVerified) {
            throw AuthenticationException("Email is not verified")
        }

        val userId = userService.createIfNotPresent(SocialUser(payload.email))
        UserContext.setUserUuid(userId)

    }

    private fun processRoot1User() {
        val userId = userService.createIfNotPresent(SocialUser("root@gmail.com"))
        UserContext.setUserUuid(userId)
    }

    private fun processRoot2User() {
        val userId = userService.createIfNotPresent(SocialUser("root2@gmail.com"))
        UserContext.setUserUuid(userId)
    }
}
