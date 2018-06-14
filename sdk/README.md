# ZappSDK by MEV
Android library (aar)

### Project Structure
One Android Activity (ZappId View);
Java classes (internal log implementation)
Bloom Filter
Tests (AndroidTestCase)
Implement this aar library to project to enable event logging. 
### images
Images used in app and icons for all devices
### lib
3rd party libraries

### Dependencies
All 3rd party libraries in lib directory
- [LoggerSDK] - Library provides functions to store logs and send them to server.
- [gson-2.2.jar] - Library provides functions to work with JSON format.

## Manual
**Before starting using zapp methods, developer should init app info (appName as string, appId as string, appVersion as string) zapptitude.initAppInfo(appName, appId, appVersion);**

To request zappId zapptitude.requestZappId();

To set zappId (zappId as string) zapptitude.setZappId(zappId);

To get user provider zappId zapptitude.userProviderZappId();


**Events: Log event (event as string) zapptitude.logEvent(event);**

Log begin task (task as string, context as string) zapptitude.logBeginTask(task, context);

Log solved binary task (task as string, context as string, topics as string, expected as boolean, actual as boolean) zapptitude.logSolveBinaryTask( task, context, topics, expected, actual);

Log solved int task (task as string, context as string, topics as string, expected as int, actual as int) zapptitude.logSolveIntTask(task, context, topics, expected, actual);

Log solved float task (task as string, context as string, topics as string, expected as float, actual as float) zapptitude.logSolveFloatTask(task, context, topics, expected, actual);

Log solved MC task (task as string, context as string, topics as string, expected as char, actual as char, among as int) zapptitude.logSolveMCTask(task, context, topics, expected, actual, among);

Log solved grad task (task as string, context as string, topics as string, expected as int, actual as int, among as int) zapptitude.logSolveGradTask(task, context, topics, expected, actual, among);


**Please implement this methods to your every activity to provide stoping and resuming background process of log collectioning in your app.**

zapptitude.onResume() in onResume() method of activity

zapptitude.onStop() in onStop() method of activity

**Environments**

To switch environment on ZappSDK change **URL type** in ZappInternal() constructor.
Example: Logger.getInstance().initLogger(cleanedAppId, **URLType.PROD_ENVIRONMENT.getValue()**); for **Prod** or Logger.getInstance().initLogger(cleanedAppId, **URLType.DEV_ENVIRONMENT.getValue()**); for **Dev**