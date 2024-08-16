package com.notification.service.repository

import com.notification.service.model.NotificationRequest
import com.notification.service.model.WhatsAppNotificationRequest
import com.notification.service.service.TwilioService
import org.slf4j.LoggerFactory

class TwilioNotificationRepository(private val twilioService: TwilioService) : NotificationRepository {
    private val logger = LoggerFactory.getLogger(TwilioNotificationRepository::class.java)

    override fun sendNotification(notificationRequest: NotificationRequest): String {
        logger.info("Sending notification through TwilioNotificationRepository")
        return twilioService.sendSMS(notificationRequest)
    }

    override fun sendWhatsAppNotification(whatsAppNotificationRequest: WhatsAppNotificationRequest): String {
        logger.info("Sending WhatsApp notification through TwilioNotificationRepository")
        // Assuming TwilioService has a method to send WhatsApp messages
        return twilioService.sendWhatsAppMessage(whatsAppNotificationRequest)
    }
}
