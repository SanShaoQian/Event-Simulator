class Leave extends Event {
    private final double time;
    private final Customer customer;

    Leave(double time, Customer customer) {
        this.time = time;
        this.customer = customer;
    }

    double getTime() {
        return this.time;
    }

    int getCustomerNumber() {
        return this.customer.getNumber();
    }

    @Override
    public String toString() {
        return customer.toString() + " leaves\n";
    }

    @Override
    public int updateNumLeft(int numLeft) {
        return numLeft + 1;
    }
}
