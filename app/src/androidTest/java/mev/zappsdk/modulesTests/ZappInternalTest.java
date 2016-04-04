package mev.zappsdk.modulesTests;

import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mev.loggersdk.modules.LAppContextStorage;
import mev.zappsdk.modules.ZappInternal;

/**
 * Created by andrew on 25.03.16.
 */
public class ZappInternalTest extends AndroidTestCase {

    // for names: "jonh.smith", "kathy.hudson", "mae.dunn", "jacob.young"
    public final String BLOOM_FILTER_RESPONSE =
            "{\"status\":true," +
                    "\"errors\":[]," +
                    "\"result\":{" +
                    "\"set\":\"001111001011101010011111100001001001111100001010011110011\"," +
                    "\"hashes\":[" +
                    "\"VXQbYR\"," +
                    "\"vYFWZc\"," +
                    "\"TgK6LC\"," +
                    "\"ePUSgn\"," +
                    "\"bN16oS\"," +
                    "\"d50XRS\"," +
                    "\"rV58gE\"," +
                    "\"P7WQrK\"," +
                    "\"BYU694\"," +
                    "\"juBvHb\"" +
                    "]," +
                    "\"entries\":4}" +
                    "}";

    public void testBloomFilter()
    {
        String bitSet = new String();
        ArrayList<String> hashes = new ArrayList();
        try {
            JSONObject root = new JSONObject(BLOOM_FILTER_RESPONSE);
            JSONObject resultArray = root.getJSONObject("result");

            bitSet = resultArray.getString("set");
            JSONArray hashesArray  = resultArray.getJSONArray("hashes");
            for(int i = 0; i < hashesArray.length(); i++){
                hashes.add(hashesArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LAppContextStorage.appContext = getContext();

        ZappInternal zappInternal = new ZappInternal();
        zappInternal.saveBloomFilterToFileWithBitSet(bitSet, hashes);

        assertTrue(zappInternal.bloomFilter.contains("john.smith"));
        assertTrue(zappInternal.bloomFilter.contains("kathy.hudson"));
        assertFalse(zappInternal.bloomFilter.contains("obama"));
        assertTrue(zappInternal.bloomFilter.contains("mae.dunn"));
        assertTrue(zappInternal.bloomFilter.contains("jacob.young"));
        assertFalse(zappInternal.bloomFilter.contains("peeter"));
    }

}
