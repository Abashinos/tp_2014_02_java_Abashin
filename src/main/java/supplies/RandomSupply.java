package supplies;

import java.util.Random;
import static resources.RandomSupplyData.*;

public class RandomSupply {

    private static final int alphabet_len = alphabet.length();

    public static String randomStringGenerator(int size) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < size; ++i) {
            result.append( alphabet.charAt(random.nextInt(alphabet_len)) );
        }

        return result.toString();
    }
}
