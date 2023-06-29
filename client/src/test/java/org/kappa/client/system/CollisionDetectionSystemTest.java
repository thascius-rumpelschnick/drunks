package org.kappa.client.system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.entity.EntityManager;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CollisionDetectionSystemTest {

  private static final String ENTITY_ID = "some-entity";
  private static final int ZERO = 0;

  private CollisionDetectionSystem collisionDetectionSystem;
  private final EntityManager entityManager = new EntityManager();
  @Mock
  private SystemManager systemManager;


  @BeforeEach
  void beforeEach() {
    this.collisionDetectionSystem = new CollisionDetectionSystem(this.entityManager, this.systemManager);
  }


 /* @Test
  void testDetectCollision_GivenEmptyEntityComponentMap_ShouldReturnEmptyOptional() {
    final var collision = this.collisionDetectionSystem.detectCollision(ZERO, ZERO);

    assertTrue(collision.isEmpty());
  }*/


 /* @Test
  void testDetectCollision_GivenEntityComponentMapWithEntityAndNoComponents_ShouldReturnEmptyOptional() {
    this.entityManager.createEntity(ENTITY_ID);
    final var collision = this.collisionDetectionSystem.detectCollision(ZERO, ZERO);

    assertTrue(collision.isEmpty());
  }*/


 /* @Test
  void testDetectCollision_GivenEntityComponentMapWithEntityAndComponent_ShouldReturnEntryWithEntityAndComponent() {
    // Given
    final var position = new PositionComponent(ZERO, ZERO);
    this.entityManager.createEntity(ENTITY_ID);
    this.entityManager.putComponent(ENTITY_ID, position);

    // When
    final var collision = this.collisionDetectionSystem.detectCollision(ZERO, ZERO);

    // Then
    assertTrue(collision.isPresent());
    assertEquals(ENTITY_ID, collision.get().getKey());
    assertEquals(position, collision.get().getValue().get(PositionComponent.class));
  }*/

}