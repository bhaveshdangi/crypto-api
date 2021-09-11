package bhavesh.dangi.cryptoapi.service;

import bhavesh.dangi.cryptoapi.dao.UserRepository;
import bhavesh.dangi.cryptoapi.dto.User;
import bhavesh.dangi.cryptoapi.exception.DuplicateDataException;
import bhavesh.dangi.cryptoapi.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test(expected = DuplicateDataException.class)
    public void whenEmailAlreadyExists() {

        User user = User.builder()
                .name("Test")
                .email("test@gmail.com")
                .password("1234")
                .build();
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        userService.createUser(user);
    }

    @Test
    public void whenUserRegisteredSuccessful() {

        User user = User.builder()
                .name("Test")
                .email("test@gmail.com")
                .password("1234")
                .build();
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userMapper.map(user)).thenReturn(new bhavesh.dangi.cryptoapi.entity.User());

        userService.createUser(user);

        verify(userRepository, times(1)).save(Mockito.any(bhavesh.dangi.cryptoapi.entity.User.class));

    }
}
