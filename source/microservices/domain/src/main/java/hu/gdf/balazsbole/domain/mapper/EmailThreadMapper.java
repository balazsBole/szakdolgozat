package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmailMapper.class})
public interface EmailThreadMapper {

    EmailThread map(EmailThreadEntity entity);

    EmailThreadEntity map(EmailThread entity);

    @AfterMapping
    default EmailThreadEntity afterMapping(@MappingTarget EmailThreadEntity entity) {
        entity.getEmails().forEach(attachment -> attachment.setEmailThread(entity));
        return entity;
    }

    List<EmailThread> mapList(List<EmailThreadEntity> entity);
}
