package coffeeshop.util;

import java.util.Random;
import java.util.logging.Logger;

public class RandomStringGeneratorBean implements RandomStringGenerator {

    static private final Logger LOGGER = Logger.getLogger(RandomStringGeneratorBean.class.getName());

    private Random random;
    private String chars;

    public RandomStringGeneratorBean() {
        this(new Random(System.currentTimeMillis()), "abcdefghijklmnopqrstuvwxyz0123456789");
    }
    
    public RandomStringGeneratorBean(Random random, String chars) {
        this.random = random;
        this.chars = chars;
    }

    @Override
    public String generate(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int index = random.nextInt(chars.length());
            char c = chars.charAt(index);
            builder.append(c);
        }
        return builder.toString();
    }
}
