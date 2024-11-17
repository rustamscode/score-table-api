package rustamscode.scoretableapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rustamscode.scoretableapi.model.Team;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
}
