import java.util.Optional;

class Serve extends Event {
    private final double time;
    private final Customer customer;
    private final Server server;

    Serve(double time, Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        if (!server.availableNow()) {
            if (server.next() == customer) {
                this.server = server;
            } else {
                this.server = server.add(customer);
            }
        } else {
            this.server = server.add(customer);
        }
    }

    public double getTime() {
        return this.time;
    }

    public int getCustomerNumber() {
        return this.customer.getNumber();
    }

    @Override
    public String toString() {
        return String.format("%.3f %d serves by %s\n", this.time,
                this.customer.getNumber(), this.server.toString());
    }

    public Event execute(Optional<Server> optServer, ImList<Server> servers) {
        return new Done(this.time + this.customer.getServiceTime(), this.customer,this.server);
    }
    
    @Override
    public int updateNumServed(int numServed) {
        return numServed + 1;
    }

    @Override
    public ImList<Server> updateServers(ImList<Server> servers) {
        return this.server.updateServers(servers);
    }

    @Override
    public ImList<Double> updateWait(ImList<Double> wait) {
        return wait.add(this.customer.getWait(this.time));
    }
}
