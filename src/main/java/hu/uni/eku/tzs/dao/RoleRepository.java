package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
