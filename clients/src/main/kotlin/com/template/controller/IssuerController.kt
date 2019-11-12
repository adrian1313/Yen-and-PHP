package com.template.controller

import com.template.dto.IssuerToUserDTO
import com.template.dto.IssuerToUserFlowDTO
import com.template.dto.SelfIssueFlowDTO
import com.template.service.interfaces.IIssuerService
import com.template.service.interfaces.IMoveService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI


private const val CONTROLLER_NAME = "api/issuer"
@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping(CONTROLLER_NAME)
class IssuerController(private val selfIssueService : IIssuerService,
                       private val moveService: IMoveService) : BaseController()
{


    /****SelfIssue****/
    @PostMapping(value = ["/fungibleTokens"], produces = ["application/json"])
    private fun selfIssueFungibleTokens(@RequestBody request: SelfIssueFlowDTO) : ResponseEntity<Any>
    {
        return try {
            val response = selfIssueService.selfIssueService(request)
            ResponseEntity.created(URI("/$CONTROLLER_NAME/successful")).body(response)
        } catch (e: Exception) {
            this.handleException(e)
        }
    }

    /****MoveFungibleToken****/

    @PatchMapping(value = ["/fungibleTokens/moveToUser"], produces = ["application/json"])
    private fun moveTokensToUser(@RequestBody request: IssuerToUserFlowDTO) : ResponseEntity<Any>
    {
        return try {
            val response = moveService.moveTokenService(request = request)
            ResponseEntity.created(URI("successfully_transferred")).body(response)
        } catch (e: Exception) {
            this.handleException(e)
        }
    }



}