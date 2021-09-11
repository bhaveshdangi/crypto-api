package bhavesh.dangi.cryptoapi.controller;

import bhavesh.dangi.cryptoapi.dto.User;
import bhavesh.dangi.cryptoapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    private MockMvc mockMvc;

    private final RegistrationController registrationController;

    private final UserService userService;

    public RegistrationControllerTest() {

        userService = Mockito.mock(UserService.class);
        registrationController = new RegistrationController(userService);
    }

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void whenEmailIsNotValid() throws Exception {

        User user = User.builder()
                .name("test")
                .email("test")
                .password("1234")
                .build();
        String requestPayload = new ObjectMapper().writeValueAsString(user);

        this.mockMvc.perform(post("/v1/register")
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(requestPayload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenEmailIsNull() throws Exception {

        User user = User.builder()
                .name("test")
                .password("1234")
                .build();
        String requestPayload = new ObjectMapper().writeValueAsString(user);

        this.mockMvc.perform(post("/v1/register")
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(requestPayload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void whenUserRegisterSuccessful() throws Exception {

        User user = User.builder()
                .name("test")
                .email("test@gmail.com")
                .password("1234")
                .build();
        String requestPayload = new ObjectMapper().writeValueAsString(user);

        this.mockMvc.perform(post("/v1/register")
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).content(requestPayload))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
}
