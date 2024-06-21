package com.example.spring.security.repo;

import com.example.spring.security.pojos.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepo extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);
}
