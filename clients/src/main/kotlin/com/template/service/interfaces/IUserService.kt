package com.template.service.interfaces

import com.template.dto.*

interface IUserService
{
    fun registerUser(request: RegisterUserFlowDTO): UserDTO
    fun get(name: String) : Any
}
