package BrimixGaming.BraBreBrimix.Repository;

import BrimixGaming.BraBreBrimix.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByUsername(@Param("username") String username);
}
