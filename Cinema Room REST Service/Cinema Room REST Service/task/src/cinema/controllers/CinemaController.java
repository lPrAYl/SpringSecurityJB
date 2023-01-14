package cinema.controllers;

import cinema.models.Cinema;
import cinema.models.CinemaStatistics;
import cinema.models.Seat;

import cinema.models.Token;
import cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class CinemaController {

    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public Cinema getCinemaInfo() {
        return cinemaService.getCinemaInfo();
    }

    @PostMapping(path = "/purchase", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> purchaseTicket(@RequestBody Seat seatToBook) {
        return cinemaService.generateTicket(seatToBook);
    }

    @PostMapping(path = "/return", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Seat> returnTicket(@RequestBody Token token) {
        return cinemaService.returnTicket(token);
    }

    @PostMapping(path = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public CinemaStatistics getStatistics(@RequestParam(required = false) String password) {
        return cinemaService.getStatistics(password);
    }

//    @GetMapping("/seats")
//    public Cinema getSeats() {
//        return cinema;
//    }
//
//    @PostMapping("/purchase")
//    public ResponseEntity purchaseTicket(@RequestBody Seat seat) {
//        List<Seat> seats = cinema.getSeats();
//
//        if (!seats.contains(seat)) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(Map.of("error", "The number of a row or a column is out of bounds!"));
//        }
//
//        Seat foundSeat = seats.get(seats.indexOf(seat));
//
//        if (foundSeat.isPurchased()) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(Map.of("error", "The ticket has been already purchased!"));
//        }
//
//        foundSeat.setPurchased(true);
//
//        Ticket ticket = new Ticket(foundSeat);
//        cinema.addPurchasedTicket(ticket);
//
//        return ResponseEntity.ok().body(ticket);
//    }
//
//    @PostMapping("/return")
//    public ResponseEntity returnTicket(@RequestBody Ticket ticket) {
//        List<Ticket> tickets = cinema.getPurchasedTickets();
//
//        if (!tickets.contains(ticket)) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(Map.of("error", "Wrong token!"));
//        }
//
//        Ticket foundTicket = tickets.get(tickets.indexOf(ticket));
//
//        return ResponseEntity
//                .ok()
//                .body(Map.of("returned_ticket", foundTicket.getSeat()));
//
//    }
//
//    @PostMapping("/stats")
//    public ResponseEntity getPassword(@RequestParam(required = false) String password) {
//        if (password == null || !password.equals("super_secret")) {
//            return ResponseEntity
//                    .status(401)
//                    .body(Map.of("error", "The password is wrong!"));
//        }
//
//        List<Ticket> purchasedTickets = cinema.getPurchasedTickets();
//
//        int currentIncome = 0;
//        for (Ticket ticket : purchasedTickets) {
//            currentIncome += ticket.getSeat().getPrice();
//        }
//
//        Statistic statistic = new Statistic(currentIncome, 81 - purchasedTickets.size(), purchasedTickets.size());
//
//        return ResponseEntity.ok().body(statistic);
//    }
}
