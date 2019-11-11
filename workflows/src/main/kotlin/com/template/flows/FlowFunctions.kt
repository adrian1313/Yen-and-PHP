package com.template.functions

//import com.template.states.PlatformOrderState
import com.r3.corda.lib.tokens.contracts.states.FungibleToken
import com.template.states.RegisterState
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FlowLogic
import net.corda.core.identity.Party
import net.corda.core.node.services.queryBy
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

abstract class FlowFunctions : FlowLogic<SignedTransaction>()
{
    fun stringToUniqueIdentifier(id: String): UniqueIdentifier
    {
        return UniqueIdentifier.fromString(id)
    }

    fun stringToParty(name: String): Party
    {
        return serviceHub.identityService.partiesFromName(name, false).singleOrNull()
                ?: throw IllegalArgumentException("No match found for $name")
    }

    fun verifyAndSign(transaction: TransactionBuilder): SignedTransaction
    {
        transaction.verify(serviceHub)
        return serviceHub.signInitialTransaction(transaction)
    }

    fun getUserByLinearId(id: String): StateAndRef<RegisterState>
    {
        val linearId = stringToUniqueIdentifier(id)
        val criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId))
        return serviceHub.vaultService.queryBy<RegisterState>(criteria = criteria).states.single()
    }



    fun currentDateTime() : String?
    {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return current.format(formatter)
    }

    fun checkCurrency(cur: String): Boolean
    {
        return ((Currency.getInstance(cur)) !in Currency.getAvailableCurrencies())
    }

//    fun getInputByLinearID(id: String): StateAndRef<PlatformOrderState>
//    {
//        val linearId = stringToUniqueIdentifier(id)
//        val criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId))
//        return serviceHub.vaultService.queryBy<PlatformOrderState>(criteria = criteria).states.single()
//    }

    fun getFungibleTokenByCurrency(currency: String): StateAndRef<FungibleToken> {
        val criteria = QueryCriteria.VaultQueryCriteria()
        return serviceHub.vaultService.queryBy<FungibleToken>(criteria = criteria).states.find { it.state.data.tokenType.tokenIdentifier == currency
        }
                ?: throw IllegalArgumentException("Unnavailable Currency")
    }
}