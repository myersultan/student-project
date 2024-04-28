package com.example.studentproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictionScheduler {

    @Autowired
    private StudentService studentService;

    @Scheduled(fixedDelay = 10800000) // 3 hours = 3 * 60 * 60 * 1000 milliseconds
    public void evictCacheEntries() {
        studentService.evictAllCacheEntries();
    }
}
