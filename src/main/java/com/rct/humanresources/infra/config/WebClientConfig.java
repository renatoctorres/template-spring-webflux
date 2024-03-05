package com.rct.humanresources.infra.config;

import io.netty.handler.ssl.SslContextBuilder;

import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import javax.net.ssl.SSLException;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static io.netty.handler.logging.LogLevel.DEBUG;
import static io.netty.handler.ssl.util.InsecureTrustManagerFactory.INSTANCE;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static reactor.netty.transport.logging.AdvancedByteBufFormat.TEXTUAL;

/**
 * WebClient Configuration
 */
@Configuration
public class WebClientConfig {
    /**
     * Get Web Client Builder
     * @return WebClient
     * @throws SSLException SSL Exception
     */
    @Bean
    public WebClient getWebClientBuilder() throws SSLException {

        final long TIMEOUT = 120L;
        final long FACTOR = 1000L;

        // Define SSLContext in order to connect to secure APIs network
        var sslContext = SslContextBuilder
                .forClient()
                .trustManager(INSTANCE)
                .build();

        // Create an HTTP Client. Overload with timeout config and LogLevel settings.
        // Override the above SSLContext into the client
        var httpClient = HttpClient.create()
                .option(CONNECT_TIMEOUT_MILLIS, 120 * 1000)
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT * FACTOR, MILLISECONDS)))
                .wiretap("reactor.netty.http.client.HttpClient", DEBUG, TEXTUAL)
                .followRedirect(true)
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        // Add the above connector to the WebClient instance
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
