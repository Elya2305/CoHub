package com.company.config

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.apache.v2.ApacheHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class GoogleVerifierConfig {

    @Bean
    open fun googleIdTokenVerifier(@Value("\${google.audience}") audience: String): GoogleIdTokenVerifier {
        return GoogleIdTokenVerifier.Builder(ApacheHttpTransport(), GsonFactory())
            .setAudience(listOf(audience))
            .build()
    }
}