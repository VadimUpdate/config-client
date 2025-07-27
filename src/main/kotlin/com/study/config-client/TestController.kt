package com.example.demo

import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RefreshScope
@RestController
class TestController(
    private val env: Environment
) {

    @GetMapping("/test")
    fun testEndpoint(): String {
        val maxConnections = env.getProperty("max_connections", "NOT_SET")
        val enableLogging = env.getProperty("enable_logging", "NOT_SET")
        return "Max connections: $maxConnections, Logging enabled: $enableLogging"
    }
}
