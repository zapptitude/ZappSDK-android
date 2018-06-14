package mev.zappsdk.modules.Helpers.BloomFilter.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;

import mev.zappsdk.modules.Helpers.BloomFilter.BloomFilterManager;

/**
 * Created by andrew on 29.03.16.
 */
public class ZappBloom implements Serializable {

    //region Properties
    private String bitSetString;
    private BitSet bitSet;
    private ArrayList<String> seedsArray;
    //endregion

    //region Getters
    public String getBitSetString() {
        return bitSetString;
    }

    public BitSet getBitSet() {
        return bitSet != null ? bitSet : (bitSet = BloomFilterManager.createFromString(bitSetString));
    }

    public ArrayList<String> getSeedsArray() {
        return seedsArray;
    }
    //endregion

    //region Constructors
    public ZappBloom(String bitSetString, ArrayList<String> seedsArray) {
        this.bitSetString = bitSetString;
        this.seedsArray = seedsArray;
    }
    //endregion

}
