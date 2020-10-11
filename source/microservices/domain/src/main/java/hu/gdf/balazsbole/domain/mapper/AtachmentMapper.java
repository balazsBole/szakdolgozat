package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Mapper(componentModel = "spring")
public interface AtachmentMapper {

    @Mapping(source = "name", target = "filename")
    @Mapping(source = "contentType", target = "contentType")
   Attachment map(DataSource dataSource);

}
