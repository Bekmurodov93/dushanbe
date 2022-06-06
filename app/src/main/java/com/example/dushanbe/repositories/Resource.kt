package com.example.dushanbe.repositories

sealed class Resource<Type>(
    val data: Type? = null,
    val message: String? = null,
    val code: Int = 0
) {
    class Default : Resource<Any>()
    class Success<Type>(data: Type) : Resource<Type>(data = data)
    class Loading<Type> : Resource<Type>()
    class Error<Type>(message: String, code: Int = -1) :
        Resource<Type>(message = message, code = code)

    class Empty<Type> : Resource<Type>()
}