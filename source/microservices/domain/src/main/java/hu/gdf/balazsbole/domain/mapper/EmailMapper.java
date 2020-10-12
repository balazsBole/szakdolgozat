package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.entity.EmailEntity;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring", uses = ContentMapper.class)
public interface EmailMapper {

    Email map(EmailEntity entity);

    EmailEntity map(Email entity);

    @Mapping(source = "messageId", target = "header.messageId")
    @Mapping(source = "inReplyTo", target = "header.inReplyTo")
    @Mapping(source = "from", target = "header.from")
    @Mapping(source = "to", target = "header.to")
    @Mapping(source = "subject", target = "header.subject")
    @Mapping(source = "emailProtocolValue", target = "content")
    Email map(EmailProtocolValue emailProtocolValue);

    default LocalDateTime createLocalDateTimeFromEpoch(long value) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
    }


}
