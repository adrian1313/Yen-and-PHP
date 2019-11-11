package com.template.flows

import com.r3.corda.lib.tokens.contracts.states.FungibleToken
import com.r3.corda.lib.tokens.contracts.types.IssuedTokenType
import com.r3.corda.lib.tokens.contracts.types.TokenType
import com.r3.corda.lib.tokens.contracts.utilities.issuedBy
import com.r3.corda.lib.tokens.contracts.utilities.of
import com.r3.corda.lib.tokens.workflows.flows.rpc.RedeemFungibleTokens
import com.r3.corda.lib.tokens.workflows.utilities.getPreferredNotary
import com.template.contracts.RegisterContract
import com.template.functions.FlowFunctions
import com.template.states.RegisterState
import net.corda.core.contracts.Amount
import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.node.services.queryBy
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import java.lang.IllegalArgumentException


@StartableByRPC
class IssuerPHPToUserPHPFlow(private val name: String, private val amount: Double): FlowFunctions() {
    override fun call(): SignedTransaction {
        val fungibleToken = getFungibleTokenByCurrency("PHP").state.data
        val redeemedToken = amount of TokenType("PHPC", 2)


        subFlow(RedeemFungibleTokens(redeemedToken, fungibleToken.issuer))
        return subFlow(FinalityFlow(verifyAndSign(txBuilder()), listOf()))
    }

    private fun txBuilder() = TransactionBuilder(notary = getPreferredNotary(serviceHub)).apply {
        val userStateCmd = Command(RegisterContract.Commands.Update(), ourIdentity.owningKey)
        val userState = getUserByName(name)
        addInputState(getUserByName(userState.state.data.name))
        addOutputState(outState(), RegisterContract.ID)
        addCommand(userStateCmd)

    }

    private fun getUserByName(name: String)  : StateAndRef<RegisterState> {
        val criteria = QueryCriteria.VaultQueryCriteria()
        return serviceHub.vaultService.queryBy<RegisterState>(criteria = criteria).states.find {
            it.state.data.name == name
        } ?: throw IllegalArgumentException("Invalid Name")
    }


    private fun outState() : RegisterState
    {
        if (ourIdentity != stringToParty("BankPHP"))
            throw IllegalArgumentException("Only the BankPH can use in this flow")
        return RegisterState(
                name = name,
                wallet = userWallet(),
                linearId = UniqueIdentifier(),
                participants = listOf(ourIdentity)
        )
    }

    private fun userWallet(): MutableList<Amount<IssuedTokenType>>
    {
        val php = 0 of TokenType("PHP", 2) issuedBy stringToParty("BankPHP")
        val PHcurrency = 0 of TokenType("PHcurrency", 2) issuedBy stringToParty("BankPHP")
        return mutableListOf(php, PHcurrency)
    }

}



