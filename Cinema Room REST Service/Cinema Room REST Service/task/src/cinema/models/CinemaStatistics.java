package cinema.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class CinemaStatistics {

    public int currentIncome;
    public int numberOfAvailableSeats;
    public int numberOfPurchasedTickets;
}
