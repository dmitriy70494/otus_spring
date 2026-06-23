package ru.deamon.schedule.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.deamon.schedule.dto.MetricDto;

@Service
public class ExternalDataService {

    private final WebClient webClient;
    private final String externalApiUrl;

    public ExternalDataService(WebClient.Builder webClientBuilder, @Value("${external.api.url}") String externalApiUrl) {
        this.webClient = webClientBuilder.baseUrl(externalApiUrl).build();
        this.externalApiUrl = externalApiUrl;
    }

    public Mono<MetricDto> fetchData() {
        return this.webClient.get()
                .retrieve()
                .bodyToMono(MetricDto.class);
    }
}
