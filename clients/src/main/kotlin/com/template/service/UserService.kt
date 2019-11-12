package com.template.service

import com.template.common.appexceptions.NotFoundException
import com.template.dto.RegisterUserFlowDTO
import com.template.dto.UserDTO
import com.template.dto.mapToUserDTO
import com.template.flows.RegisterFlow
import com.template.service.interfaces.IUserService
import com.template.states.RegisterState
import com.template.webserver.NodeRPCConnection
import org.springframework.stereotype.Service


@Service
class UserService (private val rpc: NodeRPCConnection): IUserService {


    override fun get(name: String): Any
    {
        val userStateRef = rpc.proxy.vaultQuery(RegisterState::class.java).states
        val userState = userStateRef.find { it.state.data.name == name }
                ?: throw NotFoundException("User not found")
        return mapToUserDTO(userState.state.data)
    }

    override fun registerUser(request: RegisterUserFlowDTO): UserDTO
    {
        val flowReturn = rpc.proxy.startFlowDynamic(
                RegisterFlow::class.java,
                request.name
        )
        val flowResult = flowReturn.returnValue.get().coreTransaction.outputStates.first() as RegisterState
        return mapToUserDTO(flowResult)
    }
}