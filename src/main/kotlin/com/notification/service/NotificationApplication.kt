package com.notification.service

import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import com.notification.service.resource.NotificationResource
import com.notification.service.service.TwilioService
import com.notification.service.repository.TwilioNotificationRepository
import org.slf4j.LoggerFactory
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class TwilioConfiguration : Configuration() {
    val twilioSettings: TwilioSettings = TwilioSettings(
        System.getenv("TWILIO_ACCOUNT_SID") ?: "",
        System.getenv("TWILIO_AUTH_TOKEN") ?: ""
    )
}

data class TwilioSettings(
    val accountSid: String,
    val authToken: String
)

class NotificationApplication : Application<TwilioConfiguration>() {

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationApplication::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            NotificationApplication().run(*args)
        }
    }

    override fun getName(): String = "notification-service"

    override fun initialize(bootstrap: Bootstrap<TwilioConfiguration>) {
        // Configure Jackson for proper Kotlin support
        bootstrap.objectMapper.registerKotlinModule()
        bootstrap.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    override fun run(configuration: TwilioConfiguration, environment: Environment) {
        logger.info("Starting Notification Service")
        logger.debug("Twilio Settings: ${configuration.twilioSettings}")
        logger.debug("Initializing Twilio Service")
        val twilioService = TwilioService(configuration.twilioSettings)
        logger.debug("Initializing Twilio Notification Repository")
        val twilioNotificationRepository = TwilioNotificationRepository(twilioService)
        logger.debug("Initializing Notification Resource")
        val notificationResource = NotificationResource(twilioNotificationRepository)
        environment.jersey().register(notificationResource)
        logger.info("Notification Service started successfully")
    }
}
