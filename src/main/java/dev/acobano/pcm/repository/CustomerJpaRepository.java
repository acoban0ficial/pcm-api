package dev.acobano.pcm.repository;

import dev.acobano.pcm.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
    boolean existsByTaxId(String taxId);
    boolean existsByEmail(String email);
}
