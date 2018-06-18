package mev.zappsdk.modulesTests.Helpers.BloomFilter;

import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mev.zappsdk.modules.ZApplication;
import mev.zappsdk.modules.ZappInternal;

/**
 * Created by andrew on 30.03.16.
 */
public class BloomFilterTest extends AndroidTestCase {

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

        ZApplication.appContext = getContext();

        ZappInternal.getInstance().saveBloomFilterToFile(bitSet, hashes);

        assertTrue(ZappInternal.getInstance().bloomFilter.contains("john.smith"));
        assertTrue(ZappInternal.getInstance().bloomFilter.contains("kathy.hudson"));
        assertFalse(ZappInternal.getInstance().bloomFilter.contains("obama"));
        assertTrue(ZappInternal.getInstance().bloomFilter.contains("mae.dunn"));
        assertTrue(ZappInternal.getInstance().bloomFilter.contains("jacob.young"));
        assertFalse(ZappInternal.getInstance().bloomFilter.contains("peeter"));
    }

}
