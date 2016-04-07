package mev.zappsdk.modulesTests.Services;

import android.test.AndroidTestCase;

import mev.loggersdk.modules.LAppContextStorage;
import mev.zappsdk.modules.Services.ZappCheckZappIdService.ZappCheckZappIdService;

/**
 * Created by andrew on 30.03.16.
 */
public class ZappCheckZIDServiceTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LAppContextStorage.appContext = getContext();
    }

    public void testCheckZappId() {
        ZappCheckZappIdService.checkZappId("mikola", null);
    }

}
