package mev.zappsdk.modules;

import java.util.HashMap;

import mev.loggersdk.modules.Logger;

/**
 * Created by andrew on 24.03.16.
 */
public class Zapptitude {

    //region Constructors

    private Zapptitude() {

    }

    //endregion

    //region General methods

    public static void requestZappId() {
        ZappInternal.getInstance().requestZappId();
    }

    public static void setZappId(String zappId) {
        ZappInternal.getInstance().setZappId(zappId);
    }

    public static String userProviderZappId() {
        return ZappInternal.getInstance().userProviderZappId();
    }

    public static void logEvent(String event) {
        ZappEventLogger.getInstance().logEvent(event, ZappInternal.getInstance().sessionInfo());
    }

    public static void logBeginTask(String task, String context) {
        ZappInternal.getInstance().setTask(task, context);
        ZappEventLogger.getInstance().logBeginTask(task, context, ZappInternal.getInstance().sessionInfo());
    }

    public static void logSolveBinaryTask(String task, String context, String topics, boolean expected, boolean actual) {
        ZappEventLogger.getInstance().logSolveBinaryTask(task, context, topics, expected, actual, ZappInternal.getInstance().sessionInfoForTask(task, context));
    }

    public static void logSolveIntTask(String task, String context, String topics, int expected, int actual) {
        ZappEventLogger.getInstance().logSolveIntTask(task, context, topics, expected, actual, ZappInternal.getInstance().sessionInfoForTask(task, context));
    }

    public static void logSolveFloatTask(String task, String context, String topics, float expected, float actual) {
        ZappEventLogger.getInstance().logSolveFloatTask(task, context, topics, expected, actual, ZappInternal.getInstance().sessionInfoForTask(task, context));
    }

    public static void logSolveMCTask(String task, String context, String topics, char expected, char actual, int among) {
        ZappEventLogger.getInstance().logSolveMCTask(task, context, topics, expected, actual, among, ZappInternal.getInstance().sessionInfoForTask(task, context));
    }

    public static void logSolveGradTask(String task, String context, String topics, int expected, int actual, int among) {
        ZappEventLogger.getInstance().logSolveGradTask(task, context, topics, expected, actual, among, ZappInternal.getInstance().sessionInfoForTask(task, context));
    }

    public static void onResume() {
        Logger.getInstance().onResume();
    }

    public static void onStop() {
        Logger.getInstance().onStop();
    }

    //endregion

}
