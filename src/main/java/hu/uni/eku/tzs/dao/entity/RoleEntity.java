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
@Entity(name = "roles")
public class RoleEntity {

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private ActorEntity actor;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @Id
    @Column(name = "role")
    private String role;

}
