package br.com.gft.mvc.services;

import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gft.mvc.entities.User;
import br.com.gft.mvc.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @MockBean
    private UserRepository repository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(repository);
    }

    @Test
    public void carregarUsuarioPorUsername_retornarUsuario_quandoOUsuarioExistir() {

        final String username = randomUUID().toString();
        final String password = randomUUID().toString();

        final User user = new User(username, password);

        // @formatter:off
        final UserDetails expected = new org.springframework.security.core.userdetails.User(
                                            username,
                                            password,
                                            singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                                     );
        // @formatter:on

        given(repository.findByUsername(username)).willReturn(Optional.of(user));

        final UserDetails actual = userService.loadUserByUsername(username);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

        then(repository).should().findByUsername(username);
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void carregarUsuarioPorUsername_retornarUsuario_quandoOUsuarioNaoExistir() {

        final String username = randomUUID().toString();

        given(repository.findByUsername(username)).willReturn(Optional.empty());

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userService.loadUserByUsername(username))
                .withMessageContaining(username);

        then(repository).should().findByUsername(username);
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void salvar_retornarSalvo_quandoORegistroDoUsuarioECriado() {

        final User expected = new User();

        expected.setUsername(randomUUID().toString());
        expected.setPassword(randomUUID().toString());

        given(repository.save(expected)).willReturn(expected);

        final User actual = userService.save(expected);

        assertThat(actual).isEqualTo(expected);

        then(repository).should().save(expected);
        then(repository).shouldHaveNoMoreInteractions();
    }
}