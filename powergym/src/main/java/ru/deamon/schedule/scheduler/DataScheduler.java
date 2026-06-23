package ru.deamon.schedule.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.deamon.schedule.dto.MetricDto;
import ru.deamon.schedule.model.Metric;
import ru.deamon.schedule.service.ExternalDataService;
import ru.deamon.schedule.service.MetricService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataScheduler {

    private final ExternalDataService externalDataService;
    private final MetricService metricService;

    @Scheduled(cron = "0 * * * * *") // Every minute
    public void fetchDataAndSave() {
        log.info("Fetching data from external API...");
        externalDataService.fetchData().subscribe(this::saveMetric, error -> log.error("Error fetching data: {}", error.getMessage()));
    }

    private void saveMetric(MetricDto metricDto) {
        Metric metric = new Metric();
        metric.setData(metricDto.getData());
        metric.setPercent(metricDto.getPercent());
        metricService.saveMetric(metric);
        log.info("Successfully saved metric with data: {}", metric.getData());
    }
}
