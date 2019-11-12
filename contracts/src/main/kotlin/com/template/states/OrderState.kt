//package com.template.states
//
//import com.template.contracts.RegisterContract
//import net.corda.core.contracts.BelongsToContract
//import net.corda.core.contracts.LinearState
//import net.corda.core.contracts.UniqueIdentifier
//import net.corda.core.identity.AbstractParty
//import java.time.Instant
//
//@BelongsToContract(RegisterContract::class)
//data class OrderState(val amount: Double,
//                          val currency: String,
//                          val userId: UniqueIdentifier,
//                          val status: String, // PENDING, COMPLETED
//                          val orderDate: Instant,
//                          val transferredDate: Instant?,
//                          override val linearId: UniqueIdentifier = UniqueIdentifier(),
//                          override val participants: List<AbstractParty> ) : LinearState
//
//{
//    //fun withNewStatus(newStatus: String) = copy(status = newStatus)
//    fun complete(newStatus: String) = copy(status = newStatus, transferredDate = Instant.now())
//}