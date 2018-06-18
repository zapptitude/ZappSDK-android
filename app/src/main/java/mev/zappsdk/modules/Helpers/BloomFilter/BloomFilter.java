package mev.zappsdk.modules.Helpers.BloomFilter;

import java.util.ArrayList;
import java.util.BitSet;
import mev.zappsdk.modules.Helpers.BloomFilter.BloomFilterManager.SimpleHash;
/**
 * Created by andrew on 24.03.16.
 */
public class BloomFilter {

    //region Properties
    private int filterWithTableSize = 1<<25;

    private ArrayList<String> seeds = new ArrayList<String>() {{
        add("SxF3f");
        add("3pkfR");
        add("BVf7d");
        add("hfFF4");
        add("d64rT");
        add("RTe4t");
        add("bnFF7");
        add("MlKg8");
        add("UYt0r");
        add("cxvG3");
    }};

    private BitSet bits = new BitSet(filterWithTableSize);

    private ArrayList<SimpleHash> functions = new ArrayList();
    //endregion

    //region Constructors
    public BloomFilter(int filterWithTableSize, BitSet bitSet, ArrayList<String> seedFunctions) {
        this.filterWithTableSize = filterWithTableSize;
        this.seeds = seedFunctions;
        for (int i = 0; i < seeds.size(); i++){
            functions.add(new SimpleHash(seeds.get(i)));
        }
        this.bits = bitSet;
    }
    //endregion

    //region General methods
    public boolean contains(String value){
        if (value == null){
            return false;
        }
        boolean result = true;
        for (SimpleHash function : functions) {
            long hash = function.hash(value) & (-1L >>> 32);
            int index = (int) (hash % filterWithTableSize);
            result = result && bits.get(index);
        }
        return result;
    }

    public void loadFilter(BitSet bitSet) {
        this.bits = bitSet;
    }
    //endregion

}
