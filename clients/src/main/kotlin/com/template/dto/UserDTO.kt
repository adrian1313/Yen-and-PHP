package com.template.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.template.states.RegisterState
import java.time.Instant


/************************User Register****************************/
data class UserDTO(
        val name: String,
        val wallet: String,
        val linearId: String
)

data class RegisterUserFlowDTO @JsonCreator constructor(
        val name: String
)

fun mapToUserDTO(user: RegisterState): UserDTO
{
    return UserDTO(
            name = user.name,
            wallet = user.wallet.toString(),
            linearId = user.linearId.toString()
    )
}

