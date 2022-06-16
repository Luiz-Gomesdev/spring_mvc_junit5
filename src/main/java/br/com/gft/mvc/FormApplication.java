package br.com.gft.mvc;

import static java.util.UUID.randomUUID;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.gft.mvc.entities.Student;
import br.com.gft.mvc.repositories.StudentRepository;

@SpringBootApplication
public class FormApplication {

    @Autowired
    private StudentRepository studentRepository;

    public static void main(final String[] args) {
        SpringApplication.run(FormApplication.class, args);
    }

    @Bean
    public ApplicationRunner initializeStudents() {

        final Student student1 = new Student(randomUUID(), "Luiz", "Gomes", "31");
        final Student student2 = new Student(randomUUID(), "Alisson", "Moraes", "47");

        return args -> studentRepository.saveAll(Arrays.asList(student1, student2));
    }

}
