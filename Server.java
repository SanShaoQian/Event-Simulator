class Server {
    private final int serverNumber;
    private final int qmax;
    private final ImList<Customer> queue;

    Server(int serverNumber, int qmax) {
        this.serverNumber = serverNumber;
        this.qmax = qmax;
        this.queue = new ImList<Customer>();
    }

    Server(int serverNumber, int qmax, ImList<Customer> queue) {
        this.serverNumber = serverNumber;
        this.qmax = qmax;
        this.queue = queue;
    }

    @Override
    public String toString() {
        return String.format("%d",this.serverNumber);
    }

    public boolean availableNow() {
        return queue.isEmpty();
    }

    public boolean availableLater() {
        return qmax >= queue.size();
    }

    public Server add(Customer customer) {
        return new Server(this.serverNumber, this.qmax, this.queue.add(customer));
    }

    public Server remove() {
        return new Server(this.serverNumber, this.qmax, this.queue.remove(0));
    }

    public ImList<Server> updateServers(ImList<Server> servers) {
        return servers.set(this.serverNumber - 1, this);
    }

    Customer next() {
        return this.queue.get(0);
    }

    Server done(ImList<Server> servers) {
        return servers.get(this.serverNumber - 1).remove();
    }
}
