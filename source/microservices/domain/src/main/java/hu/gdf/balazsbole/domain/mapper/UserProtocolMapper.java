package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.kafka.user.UserProtocolValue;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserProtocolMapper {

    User map(UserProtocolValue userProtocolValue);

    default UUID map(String value) {
        return UUID.fromString(value);
    }

}
