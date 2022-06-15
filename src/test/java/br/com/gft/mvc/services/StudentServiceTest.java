package br.com.gft.mvc.services;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gft.mvc.entities.Student;
import br.com.gft.mvc.repositories.StudentRepository;

@ExtendWith(SpringExtension.class)
public class StudentServiceTest {

	@MockBean
	private StudentRepository studentRepository;

	private StudentService studentService;

	@BeforeEach
	public void setUp() {
		studentService = new StudentService(studentRepository);
	}

	@Test
	void getStudent_retornaUmAlunoQundoEleExiste() {

		final UUID id = randomUUID();

		final Student student = new Student(randomUUID(), randomUUID().toString(), randomUUID().toString());

		final Optional<Student> expected = Optional.of(student);

		given(studentRepository.findById(id)).willReturn(expected);

		final Optional<Student> actual = studentService.getStudent(id);

		assertThat(actual).isEqualTo(expected);

	}

	@Test
	void getStudent_NaoRetornaUmAlunoQuandoEleNaoExiste() {

		final UUID id = randomUUID();

		final Optional<Student> expected = Optional.empty();

		given(studentRepository.findById(id)).willReturn(expected);

		final Optional<Student> actual = studentService.getStudent(id);

		assertThat(actual).isEqualTo(expected);

	}

	@Test
	void save_retornaSalvarQuandoORegistroDoAlunoECriado() {

		final UUID id = randomUUID();

		final Student expected = new Student();
		expected.setFirstName(randomUUID().toString());
		expected.setLastName(randomUUID().toString());

		given(studentRepository.save(expected)).willAnswer(invocation -> {

			final Student toSave = invocation.getArgument(0);

			toSave.setId(id);

			return toSave;
		});

		final Student actual = studentService.save(expected);

		assertThat(actual).isEqualTo(expected);

	}
	
	@Test
	void delete_excluiAlunoQuandoEleExiste() {

		final UUID id = randomUUID();

		willDoNothing().given(studentRepository).deleteById(id);
		
		studentService.delete(id);

	}

}
