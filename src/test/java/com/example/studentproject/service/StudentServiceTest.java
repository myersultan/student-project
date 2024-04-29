package com.example.studentproject.service;

import com.example.studentproject.model.Student;
import com.example.studentproject.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService.evictAllCacheEntries();
    }

    @Test
    void testCacheHit() {
        // Arrange
        long studentId = 1L;
        Student testStudent = new Student(); // Initialize your test student object here
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(testStudent));

        // Act
        Student result1 = studentService.getStudentById(studentId); // First call
        Student result2 = studentService.getStudentById(studentId); // Second call

        // Assert
        assertEquals(testStudent, result1); // Verify that result1 is as expected
        assertEquals(testStudent, result2); // Verify that result2 is as expected
        verify(studentRepository, times(2)).findById(studentId); // Verify repository called only once
    }

    @Test
    void testCacheMiss() {
        // Arrange
        Student testStudent = new Student(); // Initialize your test student object here
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(testStudent));

        // Act
        studentService.getStudentById(1L); // First call to populate cache
        studentService.getStudentById(2L); // Second call with a different key

        // Assert
        verify(studentRepository, times(2)).findById(anyLong()); // Verify repository called twice
    }

    @Test
    void testCacheEviction() {
        // Arrange
        Student testStudent = new Student(); // Initialize your test student object here
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(testStudent));

        // Act
        studentService.getStudentById(1L); // First call to populate cache
        studentService.evictAllCacheEntries(); // Evict cache entries
        studentService.getStudentById(1L); // Second call after cache eviction

        // Assert
        verify(studentRepository, times(2)).findById(anyLong()); // Verify repository called twice
    }
}