package rustamscode.scoretableapi.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rustamscode.scoretableapi.dto.GameRequest;
import rustamscode.scoretableapi.model.Game;
import rustamscode.scoretableapi.service.GameService;

import java.util.UUID;

@RestController
@RequestMapping("/game")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameController {
    final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerGame(@RequestBody GameRequest gameRequest) {
        gameRequest.setId(UUID.randomUUID().toString());

        try {
            Game game = gameService.registerGame(gameRequest);
            return ResponseEntity.ok(game);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

}
