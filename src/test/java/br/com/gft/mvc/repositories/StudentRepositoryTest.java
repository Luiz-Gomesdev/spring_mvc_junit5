package br.com.gft.mvc.repositories;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gft.mvc.entities.Student;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void salvar_ArmazenaRegistroQuandoEValido() {

        final Student esperado = new Student();
        esperado.setFirstName(randomUUID().toString());
        esperado.setLastName(randomUUID().toString());

        final Student salvo = studentRepository.save(esperado);

        final Student actual = entityManager.find(Student.class, salvo.getId());

        assertThat(actual).isEqualTo(esperado);
    }

}
