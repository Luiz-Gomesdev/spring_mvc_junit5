package br.com.gft.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.mvc.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

}
