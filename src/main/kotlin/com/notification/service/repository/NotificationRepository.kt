package com.notification.service.repository

import com.notification.service.model.NotificationRequest

interface NotificationRepository {
    fun sendNotification(notificationRequest: NotificationRequest): String
}
