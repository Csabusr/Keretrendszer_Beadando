package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.MovieGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, Integer> {
}
