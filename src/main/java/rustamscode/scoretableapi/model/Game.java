package rustamscode.scoretableapi.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "season", nullable = false)
    Season season;

    @Column(name = "date", nullable = false)
    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    Team awayTeam;

    @Column(name = "home_score", nullable = false)
    int homeScore;

    @Column(name = "away_score", nullable = false)
    int awayScore;
}
