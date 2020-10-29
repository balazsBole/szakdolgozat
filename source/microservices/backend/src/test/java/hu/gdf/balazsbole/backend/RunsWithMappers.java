package hu.gdf.balazsbole.backend;

import hu.gdf.balazsbole.domain.mapper.ContentMapperImpl;
import hu.gdf.balazsbole.domain.mapper.EmailMapperImpl;
import hu.gdf.balazsbole.domain.mapper.EmailProtocolMapperImpl;
import hu.gdf.balazsbole.domain.mapper.EmailthreadMapperImpl;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {EmailthreadMapperImpl.class, EmailMapperImpl.class, ContentMapperImpl.class, EmailProtocolMapperImpl.class})
public interface RunsWithMappers {
}
