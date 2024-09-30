import java.util.Optional;

abstract class Event {

    int updateNumServed(int numServed) {
        return numServed;
    }

    int updateNumLeft(int numLeft) {
        return numLeft;
    }

    ImList<Double> updateWait(ImList<Double> wait) {
        return wait;
    }

    ImList<Server> updateServers(ImList<Server> servers) {
        return servers;
    }

    abstract double getTime();

    abstract int getCustomerNumber();

    public Event execute(Optional<Server> server, ImList<Server> servers) {
        return this;
    }

    Server getServer() {
        return new Server(0,0);
    }
}
