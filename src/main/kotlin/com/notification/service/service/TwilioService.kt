package com.notification.service.service

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import com.notification.service.model.NotificationRequest
import com.notification.service.model.WhatsAppNotificationRequest
import javax.ws.rs.InternalServerErrorException
import org.slf4j.LoggerFactory
import com.notification.service.TwilioSettings

open class TwilioService(private val twilioSettings: TwilioSettings) {
    private val logger = LoggerFactory.getLogger(TwilioService::class.java)

    init {
        val accountSid = twilioSettings.accountSid
        val authToken = twilioSettings.authToken
        if (accountSid.isBlank() || authToken.isBlank()) {
            logger.error("Twilio credentials not found in configuration")
            throw IllegalStateException("Twilio credentials not found in configuration")
        }
        Twilio.init(accountSid, authToken)
        logger.info("TwilioService initialized successfully")
    }

    open fun sendSMS(notificationRequest: NotificationRequest): String {
        logger.info("Attempting to send SMS to ${notificationRequest.toNumber}")
        return try {
            val message = Message.creator(
                PhoneNumber(notificationRequest.toNumber),
                PhoneNumber(notificationRequest.fromNumber),
                notificationRequest.message
            ).create()

            logger.info("SMS sent successfully. SID: ${message.sid}")
            "SMS sent successfully. SID: ${message.sid}"
        } catch (e: Exception) {
            logger.error("Failed to send SMS: ${e.message}", e)
            throw InternalServerErrorException("Failed to send SMS: ${e.message}")
        }
    }

    open fun sendWhatsAppMessage(whatsAppNotificationRequest: WhatsAppNotificationRequest): String {
        logger.info("Attempting to send WhatsApp message to ${whatsAppNotificationRequest.toNumber}")
        return try {
            val message = Message.creator(
                PhoneNumber("whatsapp:${whatsAppNotificationRequest.toNumber}"),
                PhoneNumber("whatsapp:${whatsAppNotificationRequest.fromNumber}"),
                whatsAppNotificationRequest.message
            ).create()

            logger.info("WhatsApp message sent successfully. SID: ${message.sid}")
            "WhatsApp message sent successfully. SID: ${message.sid}"
        } catch (e: Exception) {
            logger.error("Failed to send WhatsApp message: ${e.message}", e)
            throw InternalServerErrorException("Failed to send WhatsApp message: ${e.message}")
        }
    }
}
