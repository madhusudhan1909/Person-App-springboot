package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:3000") //open for specific port
@CrossOrigin() // open for all ports
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;


    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getPersons() {
        try {
            return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") long id) {
        try {

            Person perObj = getPerRec(id);

            if (perObj != null) {
                return new ResponseEntity<>(perObj, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/person")
    public ResponseEntity<Person> newPerson(@RequestBody Person person) {
        Person newPerson = personRepository
                .save(Person.builder()
                        .firstName(person.getFirstName())
                        .surName(person.getSurName())
                        .build());
        return new ResponseEntity<>(newPerson, HttpStatus.OK);
    }


    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") long id, @RequestBody Person person) {

        //check if person exist in database
        Person perObj = getPerRec(id);

        if (perObj != null) {
            perObj.setFirstName(person.getFirstName());
            perObj.setSurName(person.getSurName());
            return new ResponseEntity<>(personRepository.save(perObj), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/person/{id}")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable("id") long id) {
        try {
            Person per = getPerRec(id);

            if (per != null) {
                personRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/persons")
    public ResponseEntity<HttpStatus> deleteAllPersons() {
        try {
            personRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    private Person getPerRec(long id) {
        Optional<Person> perObj = personRepository.findById(id);

        if (perObj.isPresent()) {
            return perObj.get();
        }
        return null;
    }
    void countMethod()
    {
        long count = personRepository.count();
        System.out.println(count);
    }

}