package com.template.service


import com.r3.corda.lib.tokens.contracts.states.FungibleToken
import com.template.dto.*
import com.template.flows.IssuerToUserFlow
import com.template.flows.SelfIssueFlow
import com.template.service.interfaces.IIssuerService
import com.template.service.interfaces.IMoveService
import com.template.states.RegisterState
import com.template.webserver.NodeRPCConnection
import javassist.NotFoundException
import org.springframework.stereotype.Service

@Service
class IssuerService (private val rpc: NodeRPCConnection): IIssuerService {
    override fun getAll(): Any {
        val issuerFungibleStateAndRef = rpc.proxy.vaultQuery(FungibleToken::class.java).states
        return issuerFungibleStateAndRef.map { mapToSelfIssueDTO (it.state.data) }
    }
    override fun get(linearId: String): Any {
        val issuerFungibleStateAndRef = rpc.proxy.vaultQuery(FungibleToken::class.java).states
        val fungibleTokenState = issuerFungibleStateAndRef.find { it.state.data.tokenType.tokenIdentifier == "PHP" || it.state.data.tokenType.tokenIdentifier == "USD" }
                ?: throw NotFoundException("currency not found")
        return mapToSelfIssueDTO(fungibleTokenState.state.data)
    }
    override fun selfIssueService(request: SelfIssueFlowDTO): SelfIssueDTO {
        val flowReturn = rpc.proxy.startFlowDynamic(
                SelfIssueFlow::class.java,
                request.amount
        )
        val flowResult = flowReturn.returnValue.get().coreTransaction.outputStates.first() as FungibleToken
        return mapToSelfIssueDTO(flowResult)
    }

}

@Service
class MoveService (private val rpc: NodeRPCConnection): IMoveService {
    override fun moveTokenService(request: IssuerToUserFlowDTO): IssuerToUserDTO {
        val flowReturn = rpc.proxy.startFlowDynamic(
                IssuerToUserFlow::class.java,
                request.name,
                request.amount
        )
        val flowResult = flowReturn.returnValue.get().coreTransaction.outputStates.first() as RegisterState
        return mapToMoveTokenToPlatformDTO(flowResult)
    }

}