package design.principles.basic;

public class Delegation {
    public static void main(String[] args) {
        TicketBookingByAgent agent = new TicketBookingByAgent(new TrainTicket());
        agent.bookTicket();
    }
}

interface TravelBooking{
    public void bookTicket();
}

class TicketBookingByAgent implements TravelBooking{
    TravelBooking travelBooking;

    public TicketBookingByAgent(TravelBooking travelBooking) {
        this.travelBooking = travelBooking;
    }

    @Override
    public void bookTicket() {
        travelBooking.bookTicket();
    }
}


class TrainTicket implements TravelBooking {
    @Override
    public void bookTicket() {
        System.out.println("Train ticket booked!");
    }
}

class FlightTicket implements TravelBooking {

    @Override
    public void bookTicket() {
        System.out.println("Flight ticket booked!");
    }
}
