import java.util.Optional;
import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;

    Simulator(int numOfServers, int qmax, ImList<Double> arrivalTimes, 
           Supplier<Double> serviceTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
    }

    ImList<Server> makeServers() {
        ImList<Server> servers = new ImList<Server>();
        for (int i = 0; i < numOfServers; i++) {
            servers = servers.add(new Server(i + 1,this.qmax));
        }
        return servers;
    }

    PQ<Event> makeQueue() {
        PQ<Event> pq = new PQ<Event>(new EventComp());
        for (int i = 0; i < arrivalTimes.size(); i++) {
            Customer customer = new Customer(i + 1, this.arrivalTimes.get(i), this.serviceTimes);
            pq = pq.add(new Arrive(arrivalTimes.get(i),customer));
        }
        return pq;
    }

    Optional<Server> find(ImList<Server> servers) {
        for (Server i:servers) {
            if (i.availableNow()) {
                return Optional.<Server>of(i);
            }
        }
        for (Server j: servers) {
            if (j.availableLater()) {
                return Optional.<Server>of(j);
            }
        }
        return Optional.<Server>empty();
    }

    private Event convert(Event event) {
        return new Serve(-event.getTime(), event.getServer().next(), event.getServer());
    }

    String simulate() {
        int numServed = 0;
        int numLeft = 0;
        ImList<Double> wait = new ImList<Double>(); 
        ImList<Server> servers = this.makeServers();
        String output  = "";
        PQ<Event> pq = this.makeQueue();
        while (!(pq.isEmpty())) {
            Event event = pq.poll().first();
            pq = pq.poll().second();
            output += event.toString();
            numServed = event.updateNumServed(numServed);
            numLeft = event.updateNumLeft(numLeft);
            wait = event.updateWait(wait);
            Optional<Server> server = this.find(servers);
            Event nextEvent = event.execute(server, servers);
            servers = event.updateServers(servers);
            if (!(event == nextEvent)) {
                if (nextEvent.getTime() < 0) {  //done to serve (to prevent cyclic dependency)
                    nextEvent = this.convert(nextEvent);
                }
                pq = pq.add(nextEvent);
            } 
        }
        double aveWait = 0;
        if (!wait.isEmpty()) {
            for (double i : wait) {
                aveWait += i;
            }
            aveWait = aveWait / wait.size();
        }
        return output + String.format("[%.3f %s %s]", aveWait, numServed, numLeft);
    }
}
