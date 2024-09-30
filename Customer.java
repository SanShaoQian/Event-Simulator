import java.util.function.Supplier;

class Customer {
    private final int customerNumber;
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;

    Customer(int customerNumber, double arrivalTime, Supplier<Double> serviceTime) {
        this.customerNumber = customerNumber;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getNumber() {
        return this.customerNumber;
    }

    public double getServiceTime() {
        return this.serviceTime.get();
    }

    @Override
    public String toString() {
        return String.format("%.3f %d", this.arrivalTime, this.customerNumber);
    }

    public double getWait(double time) {
        return time - this.arrivalTime;
    }
}

