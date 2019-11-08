package com.template.flows

import co.paralleluniverse.fibers.Suspendable
import com.r3.corda.lib.tokens.contracts.types.TokenType
import com.r3.corda.lib.tokens.contracts.utilities.heldBy
import com.r3.corda.lib.tokens.contracts.utilities.issuedBy
import com.r3.corda.lib.tokens.contracts.utilities.of
import com.r3.corda.lib.tokens.workflows.flows.issue.IssueTokensFlow
import com.template.functions.FlowFunctions
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction
import java.lang.IllegalArgumentException

@StartableByRPC
class IssuePHPFlow(private val amount: Double) : FlowFunctions() {

    @Suspendable
    override fun call(): SignedTransaction {
        if (ourIdentity != stringToParty("BankPHP"))
            throw IllegalArgumentException("Only the BankPH can use in this flow")

        val getToken = subFlow(IssueTokensFlow(amount of TokenType("PHcurrency", 2) issuedBy stringToParty("BankPHP") heldBy stringToParty("BankPHP")))
        return subFlow(FinalityFlow(getToken, listOf()))
    }

}