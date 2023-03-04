package com.company.exception

class AuthenticationException(message: String) : RuntimeException(message)

class EntityNotFoundException(id: String) : RuntimeException(id)

class ForbiddenActionException(message: String) : RuntimeException(message)