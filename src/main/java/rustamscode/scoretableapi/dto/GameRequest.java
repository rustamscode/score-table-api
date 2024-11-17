package rustamscode.scoretableapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import rustamscode.scoretableapi.model.Team;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameRequest {
    String id;
    Team homeTeam;
    Team awayTeam;
    int homeScore;
    int awayScore;
}
