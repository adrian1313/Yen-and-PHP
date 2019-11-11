package com.template.flows

import com.r3.corda.lib.tokens.contracts.utilities.heldBy
import com.r3.corda.lib.tokens.contracts.utilities.issuedBy
import com.r3.corda.lib.tokens.contracts.utilities.of
import com.r3.corda.lib.tokens.money.FiatCurrency
import com.r3.corda.lib.tokens.workflows.flows.issue.IssueTokensFlow
import com.template.functions.FlowFunctions
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction

class SelfIssueFlow {
    @StartableByRPC
    class SelfIssueFlow(private val amount: Double,
                        private val currency: String): FlowFunctions() {
        override fun call(): SignedTransaction {

            /*if (ourIdentity != stringToParty("Issuer"))
                throw FlowException("Platform cannot Self Issue")*/

            val token = FiatCurrency.getInstance(currency)
            return subFlow(IssueTokensFlow(amount of token issuedBy ourIdentity heldBy ourIdentity))
//        return subFlow(IssueTokens(listOf(amount of token issuedBy ourIdentity heldBy ourIdentity)))
        }
    }
}