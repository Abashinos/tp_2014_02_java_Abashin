package supplies;

import resources.RandomSupplyData;
import resources.resource_system.ResourceFactory;

import java.util.Random;
import java.util.UUID;

public class RandomSupply {

    private static Random random;

    public static String randomStringGenerator(int size) {
        RandomSupplyData randomSupplyData = (RandomSupplyData) ResourceFactory.getInstance().get("randomSupplyData");
        String alphabet = randomSupplyData.alphabet;
        StringBuilder result = new StringBuilder();
        random = new Random();

        for (int i = 0; i < size; ++i) {
            result.append( alphabet.charAt(random.nextInt(alphabet.length())) );
        }

        return result.toString();
    }

    public static Long randomUserIdGenerator() {
        random = new Random();

        return random.nextLong();
    }

    public static String randomSessionIdGenerator() {
        return UUID.randomUUID().toString();
    }
}
