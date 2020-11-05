package hu.gdf.balazsbole.domain.enumeration;

public enum ChangeType {
    insert((byte) 0), update((byte) 1), delete((byte) 2);

    private final byte revType;

    ChangeType(byte revType) {
        this.revType = revType;
    }

    public boolean revTypeIs(byte revtype) {
        return this.revType - revtype == 0;
    }
}
