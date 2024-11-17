CREATE TABLE IF NOT EXISTS games
(
    id           BIGSERIAL PRIMARY KEY,
    season       VARCHAR(50) NOT NULL,
    date         DATE        NOT NULL,
    home_team_id BIGINT      NOT NULL REFERENCES teams (id),
    away_team_id BIGINT      NOT NULL REFERENCES teams (id),
    home_score   INT,
    away_score   INT
);