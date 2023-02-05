package com.company.exception

class AuthenticationException(message: String): RuntimeException(message)

class EntityNotFoundException(message: String): RuntimeException(message)