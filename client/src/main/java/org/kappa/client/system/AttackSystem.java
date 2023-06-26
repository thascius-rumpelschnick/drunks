package org.kappa.client.system;

import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.entity.ClubBuilder;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.entity.VomitBuilder;
import org.kappa.client.event.AttackEvent;
import org.kappa.client.event.Listener;
import org.kappa.client.utils.AttackType;
import org.kappa.client.utils.IdHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class AttackSystem implements System, Listener<AttackEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AttackSystem.class);

  private final EntityManager entityManager;
  private final SystemManager systemManager;


  public AttackSystem(final EntityManager entityManager, final SystemManager systemManager) {
    this.entityManager = entityManager;
    this.systemManager = systemManager;
  }


  @Override
  public void updateOnEventReceived(final AttackEvent event) {
    LOGGER.debug("Event: {}.", event);
    Objects.requireNonNull(event);

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
      this.entityManager.putComponent(attackEntity, vomit.getPositionComponent());
      this.entityManager.putComponent(attackEntity, vomit.getMovementAnimationComponent());

    } else if (AttackType.CLUB == attackType) {
      final var club = this.getClub(attackEntity, direction, position);

      this.entityManager.createEntity(attackEntity);
      this.entityManager.putComponent(attackEntity, club.getRenderComponent());
      this.entityManager.putComponent(attackEntity, club.getDirectionComponent());
      this.entityManager.putComponent(attackEntity, club.getPositionComponent());
      this.entityManager.putComponent(attackEntity, club.getMovementAnimationComponent());

    } else {
      LOGGER.error("Unknown attack type: {}.", attackType);

      throw new IllegalArgumentException("Unknown attack type.");
    }

    final var renderSystem = this.systemManager.getSystem(RenderSystem.class);
    renderSystem.addEntityToGameBoard(attackEntity);
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
        .movement()
        .build();
  }


  @Override
  public void update() {

  }


}
