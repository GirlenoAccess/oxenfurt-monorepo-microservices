package br.com.lima.controllers;


import br.com.lima.model.Person;
import br.com.lima.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/person")
public class PersonController {


    @Autowired
    private PersonServices personServices;

    @GetMapping(value = "/{id}",produces  = MediaType.APPLICATION_JSON_VALUE)
    public Person findByID(@PathVariable("id") Long id){
       return personServices.findByID(id);
    }

    @GetMapping(produces  = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findALl(String id){
        return personServices.findAll();
    }

    @PostMapping(
            produces  = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Person creat(@RequestBody Person person){
        return personServices.creat(person);
    }

    @PutMapping(
            produces  = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Person update(@RequestBody Person person){
        return personServices.update(person);

    }

    @DeleteMapping(value = "/{id}",
            produces  = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        personServices.delete(id);
        return ResponseEntity.noContent().build();

    }



}
