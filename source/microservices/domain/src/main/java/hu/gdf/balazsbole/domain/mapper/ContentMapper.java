package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Content;
import hu.gdf.balazsbole.domain.entity.ContentEntity;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    Content map(ContentEntity entity);

    @InheritInverseConfiguration
    ContentEntity map(Content content);

    Content map(EmailProtocolValue emailProtocolValue);

    @AfterMapping
    default ContentEntity  afterMapping(@MappingTarget ContentEntity contentEntity) {
        contentEntity.getAttachments().forEach(attachment -> attachment.setContent(contentEntity));
        return contentEntity;
    }

    default byte[] bytebufferToBytearray(java.nio.ByteBuffer byteBuffer) {
        return byteBuffer.array();
    }

}
