package rustamscode.scoretableapi.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rustamscode.scoretableapi.dto.ScoreboardUnit;
import rustamscode.scoretableapi.service.ScoreboardService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/score-table")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreboardController {
    final ScoreboardService scoreboardService;

    @Autowired
    public ScoreboardController(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }

    @GetMapping
    public ResponseEntity<List<ScoreboardUnit>> getScoreboard(@RequestParam LocalDate date) {
        List<ScoreboardUnit> scoreboard = scoreboardService.getScoreboard(date);

        return ResponseEntity.ok(scoreboard);
    }


}
