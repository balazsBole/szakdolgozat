package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
    UserEntity map(User user);

    default UserEntity update(UserEntity original, User user) {
        UserEntity userEntity = map(user);

        if (userEntity.getUsername() == null) {
            userEntity.setUsername(original.getUsername());
        }
        if (userEntity.getEmail() == null) {
            userEntity.setEmail(original.getEmail());
        }
        if (userEntity.getQueue() == null) {
            userEntity.setQueue(original.getQueue());
        }
        return userEntity;
    }

    User map(UserEntity entity);

    List<User> mapList(List<UserEntity> entity);

}
