package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Content;
import hu.gdf.balazsbole.domain.entity.ContentEntity;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    @Mapping(source = "attachmentEntities", target = "attachments")
    Content map(ContentEntity entity);

    @InheritInverseConfiguration
    ContentEntity map(Content content);

    Content map(EmailProtocolValue emailProtocolValue);

    @AfterMapping
    default void afterMapping(@MappingTarget Content content) {
        content.getAttachments().forEach(attachment -> attachment.setContent(content));
    }

    @AfterMapping
    default void afterMapping(@MappingTarget ContentEntity contentEntity) {
        contentEntity.getAttachmentEntities().forEach(attachment -> attachment.setContentEntity(contentEntity));
    }

    default byte[] bytebufferToBytearray(java.nio.ByteBuffer byteBuffer) {
        return byteBuffer.array();
    }

}
