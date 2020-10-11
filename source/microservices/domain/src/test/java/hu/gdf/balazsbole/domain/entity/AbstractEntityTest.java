package hu.gdf.balazsbole.domain.entity;


import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractEntityTest {

  @Test
  void prePersist() {
    final var entity = new AbstractEntity();
    testPrePersist(entity);
  }


  @Test
  void prePersist_withId() {
    final var entity = new AbstractEntity();
    Assertions.assertNull(entity.getId());
    final var id = UUID.randomUUID();
    entity.setId(id);
    Assertions.assertNotNull(entity.getId());

    entity.prePersist();

    Assertions.assertEquals(id, entity.getId());

    // DO NOTHING
    entity.preUpdate();
    Assertions.assertEquals(id, entity.getId());
  }


  public static void testPrePersist(final AbstractEntity entity) {
    Assertions.assertNull(entity.getId());
    entity.prePersist();
    Assertions.assertNotNull(entity.getId());
  }


  @Test
  void equals() {
    final var one = new AbstractEntity();
    one.setId(UUID.randomUUID());
    final var two = new AbstractEntity();
    two.setId(UUID.randomUUID());

    Assertions.assertNotEquals(one, two);

    two.clearId();
    two.setId(one.getId());
    Assertions.assertEquals(one, two);

    two.clearId();
    two.setId(null);
    Assertions.assertNotEquals(one, two);

    one.clearId();
    one.setId(null);
    Assertions.assertNotEquals(one, two);

    two.clearId();
    two.setId(UUID.randomUUID());
    Assertions.assertNotEquals(one, two);
  }


  @Test
  void setId() {
    final var entity = new AbstractEntity();
    Assertions.assertNull(entity.getId());
    final var id = UUID.randomUUID();
    entity.setId(id);
    Assertions.assertNotNull(entity.getId());

    entity.setId(UUID.randomUUID());

    Assertions.assertEquals(id, entity.getId());
  }


  @Test
  void test_hashCode() {
    Assertions.assertEquals(42, new AbstractEntity().hashCode());
  }


  @Test
  void test_equals() {
    final var entity1 = new AbstractEntity();
    final var entity2 = new AbstractEntity();

    Assertions.assertTrue(entity1.equals(entity1));
    Assertions.assertFalse(entity1.equals(new AbstractEntityTest()));

    Assertions.assertFalse(entity1.equals(entity2));

    entity1.setId(UUID.randomUUID());
    Assertions.assertFalse(entity1.equals(entity2));

    entity2.setId(entity1.getId());
    Assertions.assertTrue(entity1.equals(entity2));

    entity2.clearId();
    entity2.setId(UUID.randomUUID());
    Assertions.assertFalse(entity1.equals(entity2));
  }


  @Test
  void equalsWithId() {
    final var one = new AbstractEntity();
    final var two = new AbstractEntity();

    Assertions.assertTrue(AbstractEntity.equalsWithId(one, one));
    Assertions.assertTrue(AbstractEntity.equalsWithId(one, two));
    Assertions.assertTrue(AbstractEntity.equalsWithId(two, one));

    Assertions.assertTrue(AbstractEntity.equalsWithId(null, (AbstractEntity) null));
    Assertions.assertFalse(AbstractEntity.equalsWithId(one, (AbstractEntity) null));
    Assertions.assertFalse(AbstractEntity.equalsWithId(null, one));

    one.setId(UUID.randomUUID());
    Assertions.assertFalse(AbstractEntity.equalsWithId(one, two));
    Assertions.assertFalse(AbstractEntity.equalsWithId(two, one));

    two.setId(UUID.randomUUID());
    Assertions.assertFalse(AbstractEntity.equalsWithId(one, two));
    Assertions.assertFalse(AbstractEntity.equalsWithId(two, one));

    two.clearId();
    two.setId(one.getId());
    Assertions.assertTrue(AbstractEntity.equalsWithId(one, two));
    Assertions.assertTrue(AbstractEntity.equalsWithId(two, one));
  }


  @Test
  void equalsWithId2() {
    final UUID two = UUID.randomUUID();
    Assertions.assertTrue(AbstractEntity.equalsWithId(null, (UUID) null));
    Assertions.assertFalse(AbstractEntity.equalsWithId(null, two));

    final var one = new AbstractEntity();
    Assertions.assertTrue(AbstractEntity.equalsWithId(one, (UUID) null));
    Assertions.assertFalse(AbstractEntity.equalsWithId(one, two));

    one.setId(UUID.randomUUID());
    Assertions.assertTrue(AbstractEntity.equalsWithId(one, (UUID) null));
    Assertions.assertFalse(AbstractEntity.equalsWithId(one, two));

    Assertions.assertTrue(AbstractEntity.equalsWithId(one, one));
    Assertions.assertFalse(AbstractEntity.equalsWithId(one, two));
  }

}
