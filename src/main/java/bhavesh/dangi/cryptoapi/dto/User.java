package bhavesh.dangi.cryptoapi.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {


    @NotNull(message = "name is mandatory")
    private String name;

    @Email(message = "email is not valid")
    @NotNull(message = "email is mandatory")
    private String email;

    @NotNull(message = "password is mandatory")
    private String password;
}
