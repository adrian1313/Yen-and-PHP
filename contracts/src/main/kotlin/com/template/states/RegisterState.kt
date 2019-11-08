package com.template.states

import com.r3.corda.lib.tokens.contracts.types.IssuedTokenType
import com.template.contracts.RegisterContract
import net.corda.core.contracts.Amount
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party

@BelongsToContract(RegisterContract::class)
data class RegisterState(
        val name: String,
        val wallet: MutableList<Amount<IssuedTokenType>>,
        override val linearId: UniqueIdentifier,
        override val participants: List<Party>) : LinearState
