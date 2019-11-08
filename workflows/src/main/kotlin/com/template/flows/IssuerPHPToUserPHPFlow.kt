package com.template.flows

import com.r3.corda.lib.tokens.contracts.types.IssuedTokenType
import com.r3.corda.lib.tokens.contracts.types.TokenType
import com.r3.corda.lib.tokens.contracts.utilities.issuedBy
import com.r3.corda.lib.tokens.contracts.utilities.of
import com.template.functions.FlowFunctions
import net.corda.core.contracts.Amount
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction
import java.lang.IllegalArgumentException


@StartableByRPC
class IssuerPHPToUserPHPFlow(private val name: String, private val amount: Double): FlowFunctions() {
    override fun call(): SignedTransaction {
        if (ourIdentity != stringToParty("BankPHP"))
        throw IllegalArgumentException("Only the BankPH can use in this flow")


        val newWallet: MutableList<Amount<IssuedTokenType>> = mutableListOf()
        val redeemToken = amount of TokenType ("PHPcurrency", 2) issuedBy stringToParty("BankPHP")

        val userState = getUserByName(name).state.data
        userState.wallet.forEach {it.Amount<IssuedTokenType>

            if(it.token == redeemToken.token)
            {

            }
        }
    }

    private fun getUserByName(): Any {

    }
