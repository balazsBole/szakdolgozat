package hu.gdf.balazsbole.email.mapper;

public class MappingException  extends RuntimeException{
    public MappingException(Exception e) {
        super(e.getMessage(), e);
     this.setStackTrace(e.getStackTrace());
    }
}
