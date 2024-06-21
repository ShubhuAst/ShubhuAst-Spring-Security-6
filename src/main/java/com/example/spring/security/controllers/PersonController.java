package com.example.spring.security.controllers;

import com.example.spring.security.pojos.GrantedAuthorityImpl;
import com.example.spring.security.pojos.Person;
import com.example.spring.security.repo.PersonRepo;
import com.example.spring.security.saveCO.PersonSaveCO;
import com.example.spring.security.saveCO.PersonUpdateCO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    final PersonRepo personRepo;
    final ModelMapper modelMapper;
    final PasswordEncoder passwordEncoder;

    @PostMapping("/save")
    public String savePerson(@RequestBody PersonSaveCO personSaveCO) {
        Optional<Person> optionalPerson = personRepo.findByEmail(personSaveCO.getEmail());
        if (!optionalPerson.isEmpty())
            return "Already Exist";

        Person person = modelMapper.map(personSaveCO, Person.class);

        GrantedAuthorityImpl grantedAuthority = new GrantedAuthorityImpl();
        grantedAuthority.setAuthority(personSaveCO.getRole());
        person.setGrantedAuthorities(Collections.singletonList(grantedAuthority));

        person.setPassword(passwordEncoder.encode(person.getPassword()));

        personRepo.save(person);
        return "Person Saved";
    }

    @GetMapping("/get/{id}")
    public Person getPerson(@PathVariable Long id) {
        Optional<Person> person = personRepo.findById(id);
        if (!person.isPresent())
            return null;
        return person.get();
    }

    @PutMapping("/update")
    public String updatePerson(@RequestBody @Valid PersonUpdateCO personUpdateCO) {
        Optional<Person> optionalPerson = personRepo.findById(personUpdateCO.getId());
        if (!optionalPerson.isPresent()) {
            return "Person doesnot exist";
        }
        Person person = optionalPerson.get();
        if (personUpdateCO.getPassword() != null)
            personUpdateCO.setPassword(passwordEncoder.encode(personUpdateCO.getPassword()));

        modelMapper.map(personUpdateCO, person);
        personRepo.save(person);
        return "Person Updated";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePerson(@PathVariable Long id) {
        personRepo.deleteById(id);
        return "Person Deleted Successfully";
    }
}
