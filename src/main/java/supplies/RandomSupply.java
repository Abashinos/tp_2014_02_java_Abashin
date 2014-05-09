package supplies;

import java.util.Random;
import java.util.UUID;

public class RandomSupply {

    private static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int alphabet_len = alphabet.length();
    private static Random random;

    public static String randomStringGenerator(int size) {
        StringBuilder result = new StringBuilder();
        random = new Random();

        for (int i = 0; i < size; ++i) {
            result.append( alphabet.charAt(random.nextInt(alphabet_len)) );
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
