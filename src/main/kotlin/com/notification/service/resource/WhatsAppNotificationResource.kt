package com.notification.service.resource

import com.notification.service.model.WhatsAppNotificationRequest
import com.notification.service.repository.NotificationRepository
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import org.slf4j.LoggerFactory

@Path("/api/v1/whatsappNotifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class WhatsAppNotificationResource(private val notificationRepository: NotificationRepository) {

    private val logger = LoggerFactory.getLogger(WhatsAppNotificationResource::class.java)

    @POST
    fun sendWhatsAppNotification(@Valid whatsAppNotificationRequest: WhatsAppNotificationRequest): Response {
        logger.info("Received WhatsApp notification request: $whatsAppNotificationRequest")
        return try {
            val result = notificationRepository.sendWhatsAppNotification(whatsAppNotificationRequest)
            logger.info("WhatsApp message sent successfully: $result")
            Response.ok(result).build()
        } catch (e: BadRequestException) {
            logger.warn("Bad request: ${e.message}")
            Response.status(Response.Status.BAD_REQUEST)
                .entity(mapOf("error" to e.message))
                .build()
        } catch (e: InternalServerErrorException) {
            logger.error("Internal server error while sending WhatsApp message", e)
            Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(mapOf("error" to "An error occurred while sending the WhatsApp message"))
                .build()
        } catch (e: Exception) {
            logger.error("Unexpected error occurred", e)
            Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(mapOf("error" to "An unexpected error occurred"))
                .build()
        }
    }
}
