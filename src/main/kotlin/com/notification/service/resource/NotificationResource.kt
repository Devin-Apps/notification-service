package com.notification.service.resource

import com.notification.service.model.NotificationRequest
import com.notification.service.repository.NotificationRepository
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import org.slf4j.LoggerFactory

@Path("/api/v1/SMSnotifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class NotificationResource(private val notificationRepository: NotificationRepository) {

    private val logger = LoggerFactory.getLogger(NotificationResource::class.java)

    @POST
    fun sendNotification(@Valid notificationRequest: NotificationRequest): Response {
        logger.info("Received notification request: $notificationRequest")
        return try {
            val result = notificationRepository.sendNotification(notificationRequest)
            logger.info("SMS sent successfully: $result")
            Response.ok(result).build()
        } catch (e: BadRequestException) {
            logger.warn("Bad request: ${e.message}")
            Response.status(Response.Status.BAD_REQUEST)
                .entity(mapOf("error" to e.message))
                .build()
        } catch (e: InternalServerErrorException) {
            logger.error("Internal server error while sending SMS", e)
            Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(mapOf("error" to "An error occurred while sending the SMS"))
                .build()
        } catch (e: Exception) {
            logger.error("Unexpected error occurred", e)
            Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(mapOf("error" to "An unexpected error occurred"))
                .build()
        }
    }
}
