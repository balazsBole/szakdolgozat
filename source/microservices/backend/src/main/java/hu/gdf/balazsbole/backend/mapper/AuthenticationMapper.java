package hu.gdf.balazsbole.backend.mapper;

import hu.gdf.balazsbole.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.Authentication;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    @Mapping(source = "name", target = "keycloakID")
    @Mapping(expression = "java(((org.springframework.security.oauth2.jwt.Jwt) authentication.getCredentials()).getClaimAsString(\"preferred_username\"))", target = "username")
    UserEntity map(Authentication authentication);

    default UUID map(String string) {
        return UUID.fromString(string);
    }
}
