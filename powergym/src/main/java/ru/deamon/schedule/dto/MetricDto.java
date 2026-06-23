package ru.deamon.schedule.dto;

import lombok.Data;

@Data
public class MetricDto {
    private Long id;
    private String data;
    private Double percent;
}
