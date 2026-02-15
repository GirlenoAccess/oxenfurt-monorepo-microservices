package br.com.lima.services;

import br.com.lima.exception.ResourceNotFoundException;
import br.com.lima.model.Person;
import br.com.lima.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;



@Service
public class PersonServices {

    private final PersonRepository personRepository;
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());


    public PersonServices(PersonRepository repository) {
        this.personRepository = repository;
    }

    public Person findByID (Long id){
        logger.info("Finding person by ID: " + id);
        return personRepository.findById(id).orElseThrow((()-> new ResourceNotFoundException("ID nao encontrado")));

    }

    public List<Person> findAll(){
        logger.info("Finding all people ");
        return personRepository.findAll();
    }


    public Person creat (Person person){
        logger.info("Creating one people" );
        return personRepository.save(person);
    }


    public Person update (Person person){
        logger.info("Update one people" );
        Person entity = personRepository.findById(person.getId()).orElseThrow((()-> new ResourceNotFoundException("ID nao encontrado")));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAdress(person.getAdress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete (Long id){
        logger.info("Deleting one people" );
        Person entity = personRepository.findById(id).orElseThrow((()-> new ResourceNotFoundException("ID nao encontrado")));
        personRepository.delete(entity);

    }




}
