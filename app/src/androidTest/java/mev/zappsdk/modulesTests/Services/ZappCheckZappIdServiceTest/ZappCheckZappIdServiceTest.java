package mev.zappsdk.modulesTests.Services.ZappCheckZappIdServiceTest;

import android.test.AndroidTestCase;

import mev.zappsdk.modules.Helpers.ZappResultHandler;
import mev.zappsdk.modules.Services.ZappCheckZappIdService.ZappCheckZappIdService;
import mev.zappsdk.modules.ZApplication;

/**
 * Created by andrew on 30.03.16.
 */
public class ZappCheckZappIdServiceTest extends AndroidTestCase {

    public static final String TEST_NAME = "test_name";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ZApplication.appContext = getContext();
    }

    public void testCheckZappId() {

        ZappResultHandler.SuccessHandler successHandler = new ZappResultHandler.SuccessHandler() {
            @Override
            public void onSuccess(String result) {}
        };

        ZappResultHandler.FailHandler failHandler = new ZappResultHandler.FailHandler() {
            @Override
            public void onFail(String errorMessage) {
                fail(errorMessage);
            }
        };

        ZappResultHandler resultHandler = new ZappResultHandler(successHandler, failHandler);
        ZappCheckZappIdService.checkZappId(TEST_NAME, resultHandler);

    }

    public void testLoadOfflineCheckInfoSuccess() {

        ZappResultHandler.SuccessHandler successHandler = new ZappResultHandler.SuccessHandler() {
            @Override
            public void onSuccess(String result) {}
        };

        ZappResultHandler.FailHandler failHandler = new ZappResultHandler.FailHandler() {
            @Override
            public void onFail(String errorMessage) {
                fail(errorMessage);
            }
        };

        ZappResultHandler resultHandler = new ZappResultHandler(successHandler, failHandler);
        ZappCheckZappIdService.loadOfflineCheckInfo(resultHandler);

    }

}
