package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Content;
import hu.gdf.balazsbole.domain.entity.ContentEntity;
import hu.gdf.balazsbole.kafka.email.EmailProtocolValue;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.nio.ByteBuffer;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    Content map(ContentEntity entity);

    @InheritInverseConfiguration
    ContentEntity map(Content content);

    Content map(EmailProtocolValue emailProtocolValue);

    @AfterMapping
    default ContentEntity afterMapping(@MappingTarget ContentEntity contentEntity) {
        contentEntity.getAttachments().forEach(attachment -> attachment.setContent(contentEntity));
        return contentEntity;
    }

    default byte[] bytebufferToByteArray(java.nio.ByteBuffer byteBuffer) {
        return byteBuffer.array();
    }

    default java.nio.ByteBuffer byteArrayToBytebuffer(byte[] array) {
        return ByteBuffer.wrap(array);
    }

}
