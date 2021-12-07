package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
}
