package ru.deamon.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deamon.schedule.model.Metric;
import ru.deamon.schedule.repository.MetricRepository;

@Service
@RequiredArgsConstructor
public class MetricService {

    private final MetricRepository metricRepository;

    @Transactional
    public void saveMetric(Metric metric) {
        metricRepository.save(metric);
    }

    @Transactional(readOnly = true)
    public Page<Metric> getMetrics(Pageable pageable) {
        return metricRepository.findAll(pageable);
    }
}
