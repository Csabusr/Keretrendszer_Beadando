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
@Entity(name = "directors_genres")
public class DirectorGenereEntity {

    @ManyToOne
    @JoinColumn(name = "director_id")
    private DirectorEntity director;

    @Id
    @Column(name = "genre")
    private String genere;

    @Column(name = "prob")
    private float prob;
}
