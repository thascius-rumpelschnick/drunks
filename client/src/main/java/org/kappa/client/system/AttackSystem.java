package org.kappa.client.system;

import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.VelocityComponent;
import org.kappa.client.entity.ClubBuilder;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.entity.VomitBuilder;
import org.kappa.client.event.AttackEvent;
import org.kappa.client.event.EntityEvent;
import org.kappa.client.event.EventPublisher;
import org.kappa.client.event.Listener;
import org.kappa.client.util.AttackType;
import org.kappa.client.util.IdHelper;
import org.kappa.client.util.LayoutValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.kappa.client.event.EventType.ENTITY_CREATED;


public class AttackSystem implements System, Listener<AttackEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AttackSystem.class);
  private static final EventPublisher PUBLISHER = EventPublisher.getInstance();

  private final EntityManager entityManager;
  private final SystemManager systemManager;


  public AttackSystem(final EntityManager entityManager, final SystemManager systemManager) {
    Objects.requireNonNull(entityManager);
    Objects.requireNonNull(systemManager);

    this.entityManager = entityManager;
    this.systemManager = systemManager;
  }


  @Override
  public void updateOnEventReceived(final AttackEvent event) {
    Objects.requireNonNull(event);

    // LOGGER.debug("Event: {}.", event);

    final var attackType = event.getBody();
    final var attackEntity = IdHelper.createRandomUuid();

    final var entityId = event.getEntity();
    final PositionComponent position = this.entityManager.getComponent(entityId, PositionComponent.class);
    final DirectionComponent direction = this.entityManager.getComponent(entityId, DirectionComponent.class);

    if (AttackType.VOMIT == attackType) {
      final var vomit = this.getVomit(attackEntity, direction, position);

      this.entityManager.createEntity(attackEntity);
      this.entityManager.putComponent(attackEntity, vomit.getRenderComponent());
      this.entityManager.putComponent(attackEntity, vomit.getDirectionComponent());
      this.entityManager.putComponent(attackEntity, computePosition(position, direction, vomit.getVelocityComponent()));
      this.entityManager.putComponent(attackEntity, vomit.getVelocityComponent());
      this.entityManager.putComponent(attackEntity, vomit.getDamageComponent());
      this.entityManager.putComponent(attackEntity, vomit.getMovementAnimationComponent());

    } else if (AttackType.CLUB == attackType) {
      final var club = this.getClub(attackEntity, direction, position);

      this.entityManager.createEntity(attackEntity);
      this.entityManager.putComponent(attackEntity, club.getRenderComponent());
      this.entityManager.putComponent(attackEntity, club.getDirectionComponent());
      this.entityManager.putComponent(attackEntity, computePosition(position, direction, club.getVelocityComponent()));
      this.entityManager.putComponent(attackEntity, club.getVelocityComponent());
      this.entityManager.putComponent(attackEntity, club.getDamageComponent());
      this.entityManager.putComponent(attackEntity, club.getMovementAnimationComponent());

    } else {
      LOGGER.error("Unknown attack type: {}.", attackType);
      throw new IllegalArgumentException("Unknown attack type.");
    }

    PUBLISHER.publishEvent(new EntityEvent(attackEntity, ENTITY_CREATED));
  }


  private static PositionComponent computePosition(final PositionComponent position, final DirectionComponent direction, final VelocityComponent velocity) {
    var x = position.x();
    var y = position.y();

    switch (direction.getDirection()) {
      case UP -> y -= velocity.velocity();
      case DOWN -> y += velocity.velocity();
      case LEFT -> x -= velocity.velocity();
      case RIGHT -> x += velocity.velocity();
      default -> LOGGER.error("WHOOT?");
    }

    return new PositionComponent(x, y);
  }


  private VomitBuilder.Vomit getVomit(
      final String vomitId,
      final DirectionComponent direction,
      final PositionComponent position
  ) {
    return VomitBuilder.get()
        .id(vomitId)
        .render(direction.getDirection())
        .direction(direction.getDirection())
        .position(position.x(), position.y())
        .velocity(LayoutValues.GAMEBOARD_TILE)
        .damage(1)
        .movement()
        .build();
  }


  private ClubBuilder.Club getClub(
      final String clubId,
      final DirectionComponent direction,
      final PositionComponent position
  ) {
    return ClubBuilder.get()
        .id(clubId)
        .render(direction.getDirection())
        .direction(direction.getDirection())
        .position(position.x(), position.y())
        .velocity(LayoutValues.GAMEBOARD_TILE)
        .damage(1)
        .movement()
        .build();
  }

}
