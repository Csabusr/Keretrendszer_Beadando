package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "movies_genres")
public class MovieGenreEntity {

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @Id
    @Column(name = "genre")
    private String genre;
}
