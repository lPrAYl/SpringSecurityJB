package cinema.services;

import cinema.exceptions.InvalidPasswordException;
import cinema.exceptions.SeatOutOfBoundException;
import cinema.exceptions.TicketAlreadyPurchasedException;
import cinema.exceptions.WrongTokenException;
import cinema.models.Cinema;
import cinema.models.CinemaStatistics;
import cinema.models.Seat;
import cinema.models.Token;
import cinema.repositories.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CinemaService {

    private static final String PASSWORD = "super_secret";

    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public Cinema getCinemaInfo() {
        return new Cinema(cinemaRepository.getTotalRows(), cinemaRepository.getTotalColumns(),
                cinemaRepository.getAllSeats());
    }

    public Map<String, Object> generateTicket(Seat seatToBook) {
        if (!existsSeat(seatToBook)) {
            throw new SeatOutOfBoundException();
        }

        Seat seat = cinemaRepository
                .getAllSeats()
                .stream()
                .filter(s -> s.getRow() == seatToBook.getRow()
                        && s.getColumn() == seatToBook.getColumn()
                        && s.isAvailable())
                .findAny()
                .orElseThrow(TicketAlreadyPurchasedException::new);

        seat.setAvailable(false);
        return cinemaRepository.saveTickets(new Token().toString(), seat);
    }

    public Map<String, Seat> returnTicket(Token token) {
        if (!cinemaRepository.existsTicket(token.getToken())) {
            throw new WrongTokenException();
        }

        Seat seatToSetAvailable = cinemaRepository.getSeat(token.getToken());
        Seat seat = cinemaRepository
                .getAllSeats()
                .stream()
                .filter(s -> s.getRow() == seatToSetAvailable.getRow()
                        && s.getColumn() == seatToSetAvailable.getColumn())
                .findAny()
                .orElseThrow(WrongTokenException::new);
        seat.setAvailable(true);
        cinemaRepository.removeTicket(token.getToken());

        return Map.of("returned_ticket", seat);
    }

    public boolean existsSeat(Seat seat) {
        return seat.getRow() <= cinemaRepository.getTotalRows()
                && seat.getColumn() <= cinemaRepository.getTotalColumns()
                && seat.getRow() > 0
                && seat.getColumn() > 0;
    }

    public CinemaStatistics getStatistics(String password) {
        if (!isValidPassword(password)) {
            throw new InvalidPasswordException();
        }

        return CinemaStatistics.builder()
                .currentIncome(cinemaRepository.getIncome())
                .numberOfAvailableSeats(cinemaRepository.getAvailableSeats())
                .numberOfPurchasedTickets(cinemaRepository.getPurchasedTickets())
                .build();
    }

    public boolean isValidPassword(String password) {
        return PASSWORD.equals(password);
    }
}
