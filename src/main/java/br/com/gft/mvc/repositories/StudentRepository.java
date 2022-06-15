package br.com.gft.mvc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.mvc.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
}
