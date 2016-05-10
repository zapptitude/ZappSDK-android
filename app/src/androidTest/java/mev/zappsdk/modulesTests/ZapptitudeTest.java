package mev.zappsdk.modulesTests;

import android.test.AndroidTestCase;

import java.util.HashMap;

import mev.zappsdk.modules.ZappEventLogger;
import mev.zappsdk.modules.ZappInternal;
import mev.zappsdk.modules.Zapptitude;

/**
 * Created by andrew on 30.03.16.
 */
public class ZapptitudeTest extends AndroidTestCase {

    static final String TEST_ZAPP_ID = "some_id";
    static final String TEST_EVENT = "some_event";
    static final String TEST_TASK = "some_task";
    static final String TEST_CONTEXT = "some_context";
    static final String TEST_TOPICS = "some_topics";
    static final boolean TEST_BINARY_EXPECTED = true;
    static final boolean TEST_BINARY_ACTUAL = false;
    static final int TEST_INT_EXPECTED = 0;
    static final int TEST_INT_ACTUAL = 2;
    static final float TEST_FLOAT_EXPECTED = 1.4f;
    static final float TEST_FLOAT_ACTUAL = 2.4f;
    static final char TEST_CHAR_EXPECTED = 'a';
    static final char TEST_CHAR_ACTUAL = 'c';
    static final int TEST_AMONG = 15;

    static final String ZAPP_ID_IS_WRONG_EXCEPTION = "UserProvider ZappId is wrong!";

    public void testRequestZappId() {
        Zapptitude.requestZappId();
    }

    public void testSetZappId() {
        Zapptitude.setZappId(TEST_ZAPP_ID);
    }

    public void testUserProviderZappId() {
        String zappId = Zapptitude.userProviderZappId();
        assertEquals(ZAPP_ID_IS_WRONG_EXCEPTION, TEST_ZAPP_ID, zappId);
    }

    public void testLogEvent() {
        Zapptitude.logEvent(TEST_EVENT);
    }

    public void testLogBeginTask() {
        Zapptitude.logBeginTask(TEST_TASK, TEST_CONTEXT);
    }

    public void testLogSolveBinaryTask() {
        Zapptitude.logSolveBinaryTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_BINARY_EXPECTED, TEST_BINARY_ACTUAL);
    }

    public void testLogSolveIntTask() {
        Zapptitude.logSolveIntTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_INT_EXPECTED, TEST_INT_ACTUAL);
    }

    public void testLogSolveFloatTask() {
        Zapptitude.logSolveFloatTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_FLOAT_EXPECTED, TEST_FLOAT_ACTUAL);
    }

    public void testLogSolveMCTask() {
        Zapptitude.logSolveMCTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_CHAR_EXPECTED, TEST_CHAR_ACTUAL, TEST_AMONG);
    }

    public void testLogSolveGradTask() {
        Zapptitude.logSolveGradTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_INT_EXPECTED, TEST_INT_ACTUAL, TEST_AMONG);
    }


}
