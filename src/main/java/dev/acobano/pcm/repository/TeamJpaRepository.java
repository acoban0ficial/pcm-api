package dev.acobano.pcm.repository;

import dev.acobano.pcm.model.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamJpaRepository extends JpaRepository<TeamEntity, UUID> {
}
