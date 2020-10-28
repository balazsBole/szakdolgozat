package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ContentMapper.class, EmailthreadMapper.class})
public interface EmailMapper {

    @Mapping(target = "emailthread.emails", ignore = true)
    @Mapping(target = "parent.parent", ignore = true)
    @Mapping(target = "parent.emailthread", ignore = true)
    Email map(EmailEntity entity);

    EmailEntity map(Email entity);

    List<Email> mapList(List<EmailEntity> entity);

}
