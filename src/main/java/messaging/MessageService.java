package messaging;

import servlets.ISubscriber;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageService {
    private Map<Address, ConcurrentLinkedQueue<Message>> messages = new ConcurrentHashMap<>();
    private AddressService addressService = new AddressService();

    public MessageService() {

    }

    public void addService(ISubscriber subscriber){
        messages.put(subscriber.getAddress(), new ConcurrentLinkedQueue<Message>());
    }

    public void sendMessage(Message message){
        Queue<Message> messageQueue = messages.get(message.getTo());
        messageQueue.add(message);
    }

    public void execForSubscriber(ISubscriber subscriber) {
        Queue<Message> messageQueue = messages.get(subscriber.getAddress());
        if(messageQueue == null) {
            return;
        }
        while(!messageQueue.isEmpty()){
            Message message = messageQueue.poll();
            message.exec(subscriber);
        }
    }

    public AddressService getAddressService(){
        return addressService;
    }
}
