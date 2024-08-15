package com.notification.service.repository

import com.notification.service.model.NotificationRequest
import com.notification.service.service.TwilioService
import org.slf4j.LoggerFactory

class TwilioNotificationRepository(private val twilioService: TwilioService) : NotificationRepository {
    private val logger = LoggerFactory.getLogger(TwilioNotificationRepository::class.java)

    override fun sendNotification(notificationRequest: NotificationRequest): String {
        logger.info("Sending notification through TwilioNotificationRepository")
        return twilioService.sendSMS(notificationRequest)
    }
}
