package rustamscode.scoretableapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreboardUnit {
    String teamName;
    int gameCount;
    int pointCount;

    public void increaseGameCount() {
        gameCount++;
    }

    public void addWinPoints() {
        pointCount += 3;
    }

    public void addDrawPoints() {
        pointCount += 1;
    }
}
