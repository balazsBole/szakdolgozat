package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmailMapper.class})
public interface EmailthreadMapper {

    Emailthread map(EmailthreadEntity entity);

    EmailthreadEntity map(Emailthread entity);

    @AfterMapping
    default EmailthreadEntity afterMapping(@MappingTarget EmailthreadEntity entity) {
        entity.getEmails().forEach(attachment -> attachment.setEmailthread(entity));
        return entity;
    }

    List<Emailthread> mapList(List<EmailthreadEntity> entity);
}
