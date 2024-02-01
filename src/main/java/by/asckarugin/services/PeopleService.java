package by.asckarugin.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import by.asckarugin.models.Book;
import by.asckarugin.models.Person;
import by.asckarugin.repositories.PersonRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PersonRepository personRepository;

    @Autowired
    public PeopleService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findByOne(int id){
        Optional<Person> personFound = personRepository.findById(id);

        return  personFound.orElse(null);
    }
    @Transactional
    public void save(Person person){
        personRepository.save(person);
     }
    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    public List<Book> getBooksByPerson(int id){
        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        } else{
            return Collections.emptyList();
        }
    }
    public Optional<Person> getPersonByFullName(String fullName){
        return personRepository.findByFullName(fullName);
    }

    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        } else{
            return Collections.emptyList();
        }
    }
}
