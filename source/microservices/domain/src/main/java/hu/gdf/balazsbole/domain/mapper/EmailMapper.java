package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ContentMapper.class, EmailThreadMapper.class})
public interface EmailMapper {

    @Mapping(target = "emailThread.emails", ignore = true)
    @Mapping(target = "parent.parent", ignore = true)
    @Mapping(target = "parent.emailThread", ignore = true)
    Email map(EmailEntity entity);

    EmailEntity map(Email dto);

    List<Email> mapList(List<EmailEntity> entity);

}
