package com.notification.service.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class NotificationRequest @JsonCreator constructor(
    @field:NotBlank(message = "From number is required")
    @field:Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Invalid phone number format")
    @JsonProperty("fromNumber") val fromNumber: String,

    @field:NotBlank(message = "Message is required")
    @JsonProperty("message") val message: String,

    @field:NotBlank(message = "To number is required")
    @field:Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Invalid phone number format")
    @JsonProperty("toNumber") val toNumber: String
) {
    // No-argument constructor for Jackson
    constructor() : this("", "", "")
}
