package com.template.controller

import com.template.dto.RegisterUserFlowDTO
import com.template.dto.SelfIssueFlowDTO
import com.template.service.UserService
import com.template.service.interfaces.IUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI


private const val CONTROLLER_NAME = "api/users"

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping(CONTROLLER_NAME)
class UserController(private val UserService: IUserService) : BaseController()
{

    @PostMapping(value = [], produces = ["application/json"])
    private fun createUser(@RequestBody request: RegisterUserFlowDTO): ResponseEntity<Any>
    {
        return try {
            val response = UserService.registerUser(request)
            ResponseEntity.created(URI("/" + CONTROLLER_NAME + "/" + response.linearId)).body(response)
        } catch (e: Exception) {
            this.handleException(e)
        }
    }

    @GetMapping(value = ["/{name}"], produces = ["application/json"])
    private fun getUserByName(@PathVariable name: String): ResponseEntity<Any>
    {
        return try {
            val response = UserService.get(name)
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            this.handleException(e)
        }
    }
}
