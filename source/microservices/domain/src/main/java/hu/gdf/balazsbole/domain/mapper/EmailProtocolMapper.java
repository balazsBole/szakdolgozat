package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.kafka.email.EmailProtocolKey;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.*;

@Mapper(componentModel = "spring", uses = {ContentMapper.class})
public interface EmailProtocolMapper {

    @Mapping(source = "messageId", target = "header.messageId")
    @Mapping(source = "inReplyTo", target = "header.inReplyTo")
    @Mapping(source = "references", target = "header.references")
    @Mapping(source = "from", target = "header.from")
    @Mapping(source = "to", target = "header.to")
    @Mapping(source = "subject", target = "header.subject")
    @Mapping(source = "emailProtocolValue", target = "content")
    Email map(EmailProtocolValue emailProtocolValue);

    @InheritInverseConfiguration
    @Mapping(source = "content.body", target = "body")
    @Mapping(source = "content.html", target = "html")
    @Mapping(source = "content.attachments", target = "attachments")
    EmailProtocolValue mapAvroValue(Email email);

    @Mapping(source = "header.messageId", target = "messageId")
    EmailProtocolKey mapAvroKey(Email email);

    default LocalDateTime createLocalDateTimeFromEpoch(long value) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
    }

    default long createLocalDateTimeFromEpoch(LocalDateTime localDateTime) {
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        return localDateTime.toInstant(offset).toEpochMilli();
    }

}
