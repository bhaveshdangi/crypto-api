package bhavesh.dangi.cryptoapi.service;

import bhavesh.dangi.cryptoapi.dao.UserRepository;
import bhavesh.dangi.cryptoapi.entity.User;
import bhavesh.dangi.cryptoapi.exception.DuplicateDataException;
import bhavesh.dangi.cryptoapi.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public void createUser(bhavesh.dangi.cryptoapi.dto.User user) {

        log.debug("Checking email {} in database.", user.getEmail());

        boolean isEmailExists = userRepository.existsByEmail(user.getEmail());
        if (isEmailExists) {

            log.info("Email : {} is already exists.", user.getEmail());

            throw new DuplicateDataException("Email is already exists. Please use different email.");
        }

        User userEntity = userMapper.map(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
        log.info("User registered successfully with email : {}", user.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            log.info("User found in database : {}", email);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        }
        log.error("User not found in database : {}", email);
        throw new UsernameNotFoundException("user not found in database.");
    }
}
