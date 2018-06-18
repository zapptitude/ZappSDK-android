package mev.zappsdk.modules.Helpers.BloomFilter;

import java.io.UnsupportedEncodingException;
import java.util.BitSet;

import mev.zappsdk.modules.Helpers.MurmurHash;

/**
 * Created by andrew on 25.03.16.
 */
public class BloomFilterManager {

    //region Static methods
    public static BitSet createFromString(String string) {
        BitSet bitSet = new BitSet(string.length());

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '1'){
                bitSet.set(i);
            }
        }

        return bitSet;
    }
    //endregion

    //region Inner classes
    public static class SimpleHash {
        private String seed;

        public SimpleHash(String seed) {
            this.seed = seed;
        }

        public int hash(String value) {
            try {
                byte[] bytes = (seed + value).getBytes("UTF-8");
                int result = MurmurHash.hash32(bytes, bytes.length, 0);
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return 0;
        }
        //endregion
    }

}
