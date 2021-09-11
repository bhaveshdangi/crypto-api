package bhavesh.dangi.cryptoapi.mapper;

import bhavesh.dangi.cryptoapi.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(bhavesh.dangi.cryptoapi.dto.User user);
}
