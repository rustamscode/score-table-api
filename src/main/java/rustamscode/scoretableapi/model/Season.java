package rustamscode.scoretableapi.model;

import java.time.LocalDate;
import java.time.Month;

public enum Season {
    SUMMER, AUTUMN, WINTER, SPRING;

    public static Season getSeasonByDate(LocalDate date) {
        Month month = date.getMonth();

        Season[] seasons = {WINTER, WINTER,
                SPRING, SPRING, SPRING,
                SUMMER, SUMMER, SUMMER,
                AUTUMN, AUTUMN, AUTUMN,
                WINTER};

        return seasons[month.ordinal()];
    }
}





