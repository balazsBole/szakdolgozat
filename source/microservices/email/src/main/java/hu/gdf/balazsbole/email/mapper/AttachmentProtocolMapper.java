package hu.gdf.balazsbole.email.mapper;

import hu.gdf.balazsbole.kafka.email.AttachmentProtocol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.activation.DataSource;

@Mapper(componentModel = "spring")
public interface AttachmentProtocolMapper {

    @Mapping(source = "name", target = "filename")
    @Mapping(source = "contentType", target = "contentType")
    AttachmentProtocol map(DataSource dataSource);

}
