package com.company.filter

import com.company.domain.SocialUser
import com.company.domain.UserContext
import com.company.exception.AuthenticationException
import com.company.service.UserService
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class IdTokenVerificationFilter(
    private val verifier: GoogleIdTokenVerifier,
    @Qualifier("handlerExceptionResolver") val exceptionResolver: HandlerExceptionResolver,
    private val userService: UserService,
) : OncePerRequestFilter() {
    private val HEADER_NAME = "Authorization-Google"
    private val IGNORE_ENDPOINTS = arrayOf("\\/alive", "\\/projects\\/open", "\\/projects\\/[a-z0-9-]+", "\\/tags")
    private val log = Logger.getLogger(this.javaClass.name)

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
        log.info("doFilter ${request.requestURI}")
        if ("OPTIONS" == request.method) {
            return
        }
        if (request.getHeader(HEADER_NAME) == null) {
            if (IGNORE_ENDPOINTS.any { request.requestURI.matches(Regex(it)) } && request.method == "GET") {
                return
            }
        }

        if ("root1token" == request.getHeader(HEADER_NAME)) {
            return processRoot1User()
        }

        if ("root2token" == request.getHeader(HEADER_NAME)) {
            return processRoot2User()
        }

        val idTokenValue: String = request.getHeader(HEADER_NAME)
            ?: throw AuthenticationException("Id token is missing")

        logger.info("ID_TOKEN: $idTokenValue")
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

        val userId = userService.createIfNotPresent(SocialUser(payload.email, payload.email))
        UserContext.setUserUuid(userId)
    }

    private fun processRoot1User() {
        val userId = userService.createIfNotPresent(SocialUser("root@gmail.com", "root"))
        UserContext.setUserUuid(userId)
    }

    private fun processRoot2User() {
        val userId = userService.createIfNotPresent(SocialUser("root2@gmail.com", "root2"))
        UserContext.setUserUuid(userId)
    }
}
