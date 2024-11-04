package com.example.tripadvisor.dto;

import lombok.Builder;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.util.List;

@Builder
public record LocalRecommendations(
        List<String> restaurants,
        List<String> sightseeing
) {
}
