package com.template.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.r3.corda.lib.tokens.contracts.states.FungibleToken
import com.template.states.RegisterState
import java.time.Instant



/**Self issue**/
data class SelfIssueDTO (
        val tokenType: String
)

data class SelfIssueFlowDTO @JsonCreator constructor (
        val amount: Double
)
fun mapToSelfIssueDTO (issuer: FungibleToken) : SelfIssueDTO {
    return SelfIssueDTO(
            tokenType = issuer.amount.toString()
    )
}

/**Issuer to User**/
data class IssuerToUserDTO(
        val name: String,
        val wallet: String
)

data class IssuerToUserFlowDTO @JsonCreator constructor(
        val name: String,
        val amount: Double
)

fun mapToMoveTokenToPlatformDTO(myFungibleToken: RegisterState): IssuerToUserDTO
{
    return IssuerToUserDTO(
            name = myFungibleToken.name,
            wallet = myFungibleToken.wallet.toString()
    )
}
