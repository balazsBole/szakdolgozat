package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity map(User user);

    User map(UserEntity entity);

}
