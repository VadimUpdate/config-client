package com.example.demo

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class ConfigRefresher {

    private val client = HttpClient.newHttpClient()
    private val refreshUri = URI.create("http://config-client:8081/actuator/refresh")

    @PostConstruct
    fun startAutoRefresh() {
        val scheduler = Executors.newSingleThreadScheduledExecutor()

        scheduler.scheduleAtFixedRate({
            try {
                val request = HttpRequest.newBuilder()
                    .uri(refreshUri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build()

                println("Sending POST to $refreshUri")
                val response = client.send(request, HttpResponse.BodyHandlers.ofString())
                println("Response status: ${response.statusCode()}, body: ${response.body()}")
                println("Refreshed config: ${response.body()}")
            } catch (ex: Exception) {
                println("Failed to refresh config: ${ex.message}")
                ex.printStackTrace()
            }
        }, 10, 15, TimeUnit.SECONDS)
    }
}
