package hu.gdf.balazsbole.domain.mapper;

public class MappingException  extends RuntimeException{
    public MappingException(Exception e) {
        super(e.getMessage(), e);
     this.setStackTrace(e.getStackTrace());
    }
}
