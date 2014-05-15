package messaging;

import org.junit.Before;
import org.junit.Test;
import servlets.ISubscriber;

import static org.mockito.Mockito.*;

public class MessageServiceTest {
    private MessageService messageService;
    private ISubscriber subscriber;
    private Address subscriberAddress;

    @Before
    public void setUp() throws Exception {
        messageService = new MessageService();
        subscriber = mock(ISubscriber.class);
        subscriberAddress = new Address();
        when(subscriber.getAddress()).thenReturn(subscriberAddress);
    }

    @Test
    public void addServiceTest() throws Exception {
        messageService.addService(subscriber);
    }

    @Test
    public void sendMessageTest() throws Exception {
        ISubscriber sender = mock(ISubscriber.class);
        Address senderAddress = new Address();
        when(sender.getAddress()).thenReturn(senderAddress);
        messageService.addService(sender);
        ISubscriber receiver = mock(ISubscriber.class);
        Address receiverAddress = new Address();
        when(receiver.getAddress()).thenReturn(receiverAddress);
        messageService.addService(receiver);
        Message message = mock(Message.class);

        when(sender.getMessageService()).thenReturn(messageService);
        when(receiver.getMessageService()).thenReturn(messageService);
        when(message.getFrom()).thenReturn(senderAddress);
        when(message.getTo()).thenReturn(receiverAddress);

        messageService.sendMessage(message);
        verify(message, atLeastOnce()).getTo();
    }

    @Test
    public void execForSubscriberTestGood() throws Exception {
        when(subscriber.getMessageService()).thenReturn(messageService);
        messageService.addService(subscriber);

        Message message = mock(Message.class);
        when(message.getTo()).thenReturn(subscriberAddress);
        messageService.sendMessage(message);
        messageService.execForSubscriber(subscriber);

        verify(message, times(1)).exec(subscriber);
    }

    @Test (expected = NullPointerException.class)
    public void execForSubscriberTestBad() throws Exception {
        when(subscriber.getMessageService()).thenReturn(messageService);

        Message message = mock(Message.class);
        when(message.getTo()).thenReturn(subscriberAddress);
        messageService.sendMessage(message);
        messageService.execForSubscriber(subscriber);
    }
}
