import java.util.Optional;

class Wait extends Event {
    private final double time;
    private final Customer customer;
    private final Server server;

    Wait(double time, Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        this.server = server.add(customer);
    }

    public double getTime() {
        return this.time;
    }

    public int getCustomerNumber() {
        return this.customer.getNumber();
    }

    @Override
    public String toString() {
        return String.format("%.3f %d waits at %s\n", this.time,
                this.customer.getNumber(), this.server.toString());
    }

    @Override
    public ImList<Server> updateServers(ImList<Server> servers) {
        return this.server.updateServers(servers);
    }
}
