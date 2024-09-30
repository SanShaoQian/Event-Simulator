import java.util.Comparator;

class EventComp implements Comparator<Event> {
    public int compare(Event i, Event j) {
        if (i.getTime() == j.getTime()) {
            return i.getCustomerNumber() - j.getCustomerNumber();
        } else {
            double difference = i.getTime() - j.getTime();
            if (difference < 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}

