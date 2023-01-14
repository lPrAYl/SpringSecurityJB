package cinema.exceptions;

public class TicketAlreadyPurchasedException extends RuntimeException {

    public TicketAlreadyPurchasedException() {
        super("The ticket has been already purchased!");
    }
}
