package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<DirectorEntity, Integer> {
}
