package com.notification.service.service

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import com.notification.service.model.WhatsAppNotificationRequest
import com.notification.service.model.NotificationRequest
import com.notification.service.repository.NotificationRepository
import javax.ws.rs.InternalServerErrorException
import org.slf4j.LoggerFactory
import com.notification.service.TwilioSettings

open class WhatsAppNotificationService(private val twilioSettings: TwilioSettings) : NotificationRepository {
    private val logger = LoggerFactory.getLogger(WhatsAppNotificationService::class.java)

    init {
        val accountSid = twilioSettings.accountSid
        val authToken = twilioSettings.authToken
        if (accountSid.isBlank() || authToken.isBlank()) {
            logger.error("Twilio credentials not found in configuration")
            throw IllegalStateException("Twilio credentials not found in configuration")
        }
        Twilio.init(accountSid, authToken)
        logger.info("WhatsAppNotificationService initialized successfully")
    }

    override fun sendWhatsAppNotification(notificationRequest: WhatsAppNotificationRequest): String {
        logger.info("Attempting to send WhatsApp message to ${notificationRequest.toNumber}")
        return try {
            val message = Message.creator(
                PhoneNumber("whatsapp:${notificationRequest.toNumber}"),
                PhoneNumber("whatsapp:${notificationRequest.fromNumber}"),
                notificationRequest.message
            ).create()

            logger.info("WhatsApp message sent successfully. SID: ${message.sid}")
            "WhatsApp message sent successfully. SID: ${message.sid}"
        } catch (e: Exception) {
            logger.error("Failed to send WhatsApp message: ${e.message}", e)
            throw InternalServerErrorException("Failed to send WhatsApp message: ${e.message}")
        }
    }

    override fun sendNotification(notificationRequest: NotificationRequest): String {
        // This method is not implemented for WhatsApp notifications
        throw UnsupportedOperationException("Regular SMS notifications are not supported by WhatsAppNotificationService")
    }
}
