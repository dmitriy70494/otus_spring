package ru.deamon.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.deamon.schedule.model.Metric;
import ru.deamon.schedule.service.MetricService;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricController {

    private final MetricService metricService;

    @GetMapping
    public Page<Metric> getMetrics(Pageable pageable) {
        return metricService.getMetrics(pageable);
    }
}
