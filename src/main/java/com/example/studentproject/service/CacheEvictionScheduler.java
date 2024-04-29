package com.example.studentproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CacheEvictionScheduler {

    @Scheduled(fixedDelay = 1000 * 60)
    @Caching(evict = {
            @CacheEvict(value = "students", allEntries = true)
    })

    public void cleanStudentsCache() {
        log.info("Cleaned students cache");
    }
}
