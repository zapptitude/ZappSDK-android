package mev.zappsdk.modulesTests;

import android.test.AndroidTestCase;

import java.util.HashMap;

import mev.zappsdk.modules.ZappEventLogger;

/**
 * Created by andrew on 30.03.16.
 */
public class ZappEventLoggerTest extends AndroidTestCase {

    static final String TEST_SOME_TEXT = "some_text";
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

    public static final String Z_EVENT_KEY = "ZEvent";
    public static final String Z_BEGIN_TASK_KEY = "ZBeginTask";
    public static final String Z_SOLVE_TASK_KEY = "ZSolveTask";

    public static final String EVENT_KEY = "event";
    public static final String TASK_KEY = "task";
    public static final String CONTEXT_KEY = "context";
    public static final String TYPE_KEY = "type";
    public static final String EXPECTED_KEY = "expected";
    public static final String TOPICS_KEY = "topics";
    public static final String ACTUAL_KEY = "actual";
    public static final String AMONG_KEY = "among";
    public static final String BINARY_KEY = "binary";
    public static final String INT_KEY = "binary";
    public static final String FLOAT_KEY = "float";
    public static final String MC_KEY = "mc";
    public static final String GRAD_KEY = "grad";

    static final String INSTANCE_IS_NULL_EXCEPTION = "Instance is null!";
    static final String CHECK_EMPTY_STRING_EXCEPTION = "CheckEmptyString works wrong!";

    public void testInstance() {
        assertNotNull(INSTANCE_IS_NULL_EXCEPTION, ZappEventLogger.getInstance());
    }

    public void testCheckEmptyString() {
        assertEquals(CHECK_EMPTY_STRING_EXCEPTION, TEST_SOME_TEXT, ZappEventLogger.getInstance().checkEmptyString("some_text"));
        assertNull(CHECK_EMPTY_STRING_EXCEPTION, ZappEventLogger.getInstance().checkEmptyString(""));
    }

    // TODO:  move to constants
    public void testLoggerKeys() {
        assertEquals(String.format("%s is wrong!", Z_EVENT_KEY), Z_EVENT_KEY, ZappEventLogger.Z_EVENT_KEY);
        assertEquals(String.format("%s is wrong!", Z_BEGIN_TASK_KEY), Z_BEGIN_TASK_KEY, ZappEventLogger.Z_BEGIN_TASK_KEY);
        assertEquals(String.format("%s is wrong!", Z_SOLVE_TASK_KEY), Z_SOLVE_TASK_KEY, ZappEventLogger.Z_SOLVE_TASK_KEY);
        assertEquals(String.format("%s is wrong!", EVENT_KEY), EVENT_KEY, ZappEventLogger.EVENT_KEY);
        assertEquals(String.format("%s is wrong!", TASK_KEY), TASK_KEY, ZappEventLogger.TASK_KEY);
        assertEquals(String.format("%s is wrong!", CONTEXT_KEY), CONTEXT_KEY, ZappEventLogger.CONTEXT_KEY);
        assertEquals(String.format("%s is wrong!", TYPE_KEY), TYPE_KEY, ZappEventLogger.TYPE_KEY);
        assertEquals(String.format("%s is wrong!", EXPECTED_KEY), EXPECTED_KEY, ZappEventLogger.EXPECTED_KEY);
        assertEquals(String.format("%s is wrong!", TOPICS_KEY), TOPICS_KEY, ZappEventLogger.TOPICS_KEY);
        assertEquals(String.format("%s is wrong!", ACTUAL_KEY), ACTUAL_KEY, ZappEventLogger.ACTUAL_KEY);
        assertEquals(String.format("%s is wrong!", AMONG_KEY), AMONG_KEY, ZappEventLogger.AMONG_KEY);
        assertEquals(String.format("%s is wrong!", BINARY_KEY), BINARY_KEY, ZappEventLogger.BINARY_KEY);
        assertEquals(String.format("%s is wrong!", INT_KEY), INT_KEY, ZappEventLogger.INT_KEY);
        assertEquals(String.format("%s is wrong!", FLOAT_KEY), FLOAT_KEY, ZappEventLogger.FLOAT_KEY);
        assertEquals(String.format("%s is wrong!", MC_KEY), MC_KEY, ZappEventLogger.MC_KEY);
        assertEquals(String.format("%s is wrong!", GRAD_KEY), GRAD_KEY, ZappEventLogger.GRAD_KEY);
    }

    public void testLogEvent()
    {
        ZappEventLogger.getInstance().logEvent(TEST_EVENT, new HashMap<String, String>());
    }

    public void testLogBeginTask() {
        ZappEventLogger.getInstance().logBeginTask(TEST_TASK, TEST_CONTEXT, new HashMap<String, String>());
    }

    public void testLogSolveBinaryTask() {
        ZappEventLogger.getInstance().logSolveBinaryTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_BINARY_EXPECTED, TEST_BINARY_ACTUAL, new HashMap<String, String>());
    }

    public void testLogSolveIntTask() {
        ZappEventLogger.getInstance().logSolveIntTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_INT_EXPECTED, TEST_INT_ACTUAL, new HashMap<String, String>());
    }

    public void testLogSolveFloatTask() {
        ZappEventLogger.getInstance().logSolveFloatTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_FLOAT_EXPECTED, TEST_FLOAT_ACTUAL, new HashMap<String, String>());
    }

    public void testLogSolveMCTask() {
        ZappEventLogger.getInstance().logSolveMCTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_CHAR_EXPECTED, TEST_CHAR_ACTUAL, TEST_AMONG, new HashMap<String, String>());
    }

    public void testLogSolveGradTask() {
        ZappEventLogger.getInstance().logSolveGradTask(TEST_TASK, TEST_CONTEXT, TEST_TOPICS, TEST_INT_EXPECTED, TEST_INT_ACTUAL, TEST_AMONG, new HashMap<String, String>());
    }

}
