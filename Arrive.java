import java.util.Optional;

class Arrive extends Event {
    private final double time;
    private final Customer customer;

    Arrive(double time, Customer customer) {
        this.time = time;
        this.customer = customer;
    }

    public double getTime() {
        return this.time;
    }

    public int getCustomerNumber() {
        return this.customer.getNumber();
    }

    private Event nextEvent(Server server) {
        if (server.availableNow()) {
            return new Serve(this.time, this.customer, server);
        }
        return new Wait(this.time, this.customer, server);
    }

    @Override
    public String toString() {
        return this.customer.toString() + " arrives\n";
    }

    public Event execute(Optional<Server> optServer, ImList<Server> servers) {
        return optServer.map(x -> nextEvent(x)).orElse(new Leave(this.time, this.customer));    
    }
}
