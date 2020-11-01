package hu.gdf.balazsbole.domain.mapper;

import hu.gdf.balazsbole.domain.dto.Queue;
import hu.gdf.balazsbole.domain.entity.QueueEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QueueMapper {
    QueueEntity map(Queue queue);

    Queue map(QueueEntity entity);

    List<Queue> mapList(List<QueueEntity> entity);

}
