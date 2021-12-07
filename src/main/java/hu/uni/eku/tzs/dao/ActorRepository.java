package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<ActorEntity, Integer> {
}
