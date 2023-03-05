package com.company.domain

object UserContext {

    private val userUuid = ThreadLocal<String>()

    fun setUserUuid(userUuid: String?) {
        UserContext.userUuid.set(userUuid)
    }

    fun removeUserUuid() {
        userUuid.remove()
    }

    fun getUserUuid(): String? {
        return userUuid.get()
    }
}