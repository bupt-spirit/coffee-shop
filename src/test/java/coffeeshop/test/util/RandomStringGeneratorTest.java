package coffeeshop.test.util;

import coffeeshop.util.RandomStringGenerator;
import coffeeshop.util.RandomStringGeneratorBean;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(RandomStringGeneratorBean.class)
public class RandomStringGeneratorTest {
    
    @Inject
    private RandomStringGenerator generator;

    @Test
    public void testRandomStringLength() {
        for (int i = 0; i < 10; ++i) {
            String generated = generator.generate(i);
            Assert.assertEquals(generated.length(), i);
        }
    }
    
    @Test
    public void testRandomStringDifference() {
        // If the generator is ok, this test case would have ignorable chances to fail
        final int LENGTH = 128;
        String s1 = generator.generate(LENGTH);
        String s2 = generator.generate(LENGTH);
        Assert.assertNotEquals(s1, s2);
    }
}
