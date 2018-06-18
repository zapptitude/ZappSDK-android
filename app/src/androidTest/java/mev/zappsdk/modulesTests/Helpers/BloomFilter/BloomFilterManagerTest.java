package mev.zappsdk.modulesTests.Helpers.BloomFilter;

import android.test.AndroidTestCase;

import java.util.BitSet;

import mev.zappsdk.modules.Helpers.BloomFilter.BloomFilterManager;

/**
 * Created by andrew on 30.03.16.
 */
public class BloomFilterManagerTest extends AndroidTestCase {

    static final String CREATE_FROM_STRING_EXCEPTION = "CreateFromString method works wrong!";

    public final String TEST_STRING_BIT_REPRESENTATION = "010000110";
    public final int TEST_BIT_0_INDEX = 1;
    public final int TEST_BIT_1_INDEX = 6;
    public final int TEST_BIT_2_INDEX = 7;

    public void testCreateFromString() {
        BitSet bitSet = new BitSet();
        bitSet.set(TEST_BIT_0_INDEX);
        bitSet.set(TEST_BIT_1_INDEX);
        bitSet.set(TEST_BIT_2_INDEX);
        assertEquals(CREATE_FROM_STRING_EXCEPTION, bitSet, BloomFilterManager.createFromString(TEST_STRING_BIT_REPRESENTATION));
    }

}
