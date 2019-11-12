package com.template.service.interfaces

import com.template.dto.*

interface IIssuerService : IService {
    fun selfIssueService(request: SelfIssueFlowDTO) : SelfIssueDTO
}

interface IMoveService {
    fun moveTokenService(request: IssuerToUserFlowDTO) : IssuerToUserDTO
}

