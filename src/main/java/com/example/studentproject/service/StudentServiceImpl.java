package com.example.studentproject.service;

import com.example.studentproject.model.Student;
import com.example.studentproject.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    @Cacheable(value = "students", unless = "#result == null")
    @Override
    public Student getStudentById(Long id) {
        log.info("get student by id: {}", id);
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        } else {
            return null;
        }
    }

    @CacheEvict(value = "students", allEntries = true, beforeInvocation = true)
    public void evictAllCacheEntries() {
        log.info("All cache entries evicted");
        // This method will be triggered to evict all cache entries every 3 hours
    }
}
