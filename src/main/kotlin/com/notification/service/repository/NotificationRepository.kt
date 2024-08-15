package com.notification.service.repository

import com.notification.service.model.NotificationRequest
import com.notification.service.model.WhatsAppNotificationRequest

interface NotificationRepository {
    fun sendNotification(notificationRequest: NotificationRequest): String
    fun sendWhatsAppNotification(whatsAppNotificationRequest: WhatsAppNotificationRequest): String
}
