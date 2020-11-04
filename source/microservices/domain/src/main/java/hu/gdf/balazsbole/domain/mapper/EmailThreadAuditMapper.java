package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.dto.EmailThreadAudit;
import hu.gdf.balazsbole.domain.entity.EmailThreadAuditEntity;
import hu.gdf.balazsbole.domain.entity.EmailThreadEntity;
import hu.gdf.balazsbole.domain.enumeration.ChangeType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface EmailThreadAuditMapper {


    @Mapping(target = "user", source = "user.username")
    @Mapping(target = "queue", source = "queue.name")
    EmailThreadAudit map(EmailThreadAuditEntity auditEntity);

    List<EmailThreadAudit> mapList(List<EmailThreadAuditEntity> entity);

    List<EmailThread> mapListToEmailThread(List<EmailThreadEntity> entity);

    default ChangeType map(byte value) {
        Stream<ChangeType> stream = Arrays.stream(ChangeType.values());
        return stream.filter(v -> v.revTypeIs(value)).findFirst().get();
    }

}
