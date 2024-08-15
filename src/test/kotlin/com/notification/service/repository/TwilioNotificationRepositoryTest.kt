package com.notification.service.repository

import com.notification.service.model.NotificationRequest
import com.notification.service.service.TwilioService
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.*

class TwilioNotificationRepositoryTest {

    private lateinit var twilioService: TwilioService
    private lateinit var twilioNotificationRepository: TwilioNotificationRepository

    @Before
    fun setup() {
        twilioService = mock(TwilioService::class.java)
        twilioNotificationRepository = TwilioNotificationRepository(twilioService)
    }

    @Test
    fun testSendNotification() {
        // Arrange
        val notificationRequest = NotificationRequest(
            fromNumber = "+1234567890",
            message = "Test message",
            toNumber = "+0987654321"
        )
        val expectedResult = "SMS sent successfully. SID: TEST_SID"
        `when`(twilioService.sendSMS(notificationRequest)).thenReturn(expectedResult)

        // Act
        val result = twilioNotificationRepository.sendNotification(notificationRequest)

        // Assert
        assertEquals(expectedResult, result)
        verify(twilioService, times(1)).sendSMS(notificationRequest)
    }

    @Test
    fun testSendNotificationError() {
        // Arrange
        val notificationRequest = NotificationRequest(
            fromNumber = "+1234567890",
            message = "Test message",
            toNumber = "+0987654321"
        )
        val errorMessage = "Failed to send SMS"
        `when`(twilioService.sendSMS(notificationRequest)).thenThrow(RuntimeException(errorMessage))

        // Act & Assert
        val exception = assertThrows(RuntimeException::class.java) {
            twilioNotificationRepository.sendNotification(notificationRequest)
        }
        assertEquals(errorMessage, exception.message)
        verify(twilioService, times(1)).sendSMS(notificationRequest)
    }
}
