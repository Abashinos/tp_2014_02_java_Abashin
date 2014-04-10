package messaging;

import java.util.concurrent.atomic.AtomicLong;

public class Address {

    private static AtomicLong idGenerator = new AtomicLong();
    private final long id;

    public Address() {
        this.id = idGenerator.getAndIncrement();
    }

    public long getAddress() {
        return id;
    }
}
