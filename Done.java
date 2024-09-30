import java.util.Optional;

class Done extends Event {
    private final double time;
    private final Customer customer;
    private final Server server;

    Done(double time, Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        this.server = server;
    }

    public double getTime() {
        return this.time;
    }

    public int getCustomerNumber() {
        return this.customer.getNumber();
    }

    @Override
    public String toString() {
        return String.format("%.3f %d done serving by %s\n",this.time,
                this.customer.getNumber(), this.server.toString());
    }

    @Override
    public Event execute(Optional<Server> optServer, ImList<Server> servers) {
        Server server = this.server.done(servers);
        if (server.availableNow()) {
            return this;
        }
        return new Done(-this.time, server.next(), server); //prevent cyclic dependency
    }

    @Override
    public ImList<Server> updateServers(ImList<Server> servers) {
        return this.server.done(servers).updateServers(servers);
    }

    @Override
    public Server getServer() {
        return this.server;
    }
}
