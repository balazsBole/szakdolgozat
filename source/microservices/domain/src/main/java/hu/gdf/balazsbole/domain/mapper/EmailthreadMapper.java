package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.entity.EmailthreadEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = EmailMapper.class)
public interface EmailthreadMapper {

    Emailthread map(EmailthreadEntity entity);

    EmailthreadEntity map(Emailthread entity);

    @AfterMapping
    default EmailthreadEntity afterMapping(@MappingTarget EmailthreadEntity entity) {
        entity.getEmails().forEach(attachment -> attachment.setEmailthread(entity));
        if (entity.getUser() != null)
            entity.getUser().getEmailthreads().add(entity);
        return entity;
    }

}
