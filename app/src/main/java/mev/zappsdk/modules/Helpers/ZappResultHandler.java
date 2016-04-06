package mev.zappsdk.modules.Helpers;

/**
 * Created by andrew on 05.04.16.
 */
public class ZappResultHandler {

    //region Properties

    private SuccessHandler successHandler;
    private FailHandler failHandler;

    //endregion

    //region Constructors

    public ZappResultHandler(SuccessHandler successHandler, FailHandler failHandler) {
        this.successHandler = successHandler;
        this.failHandler = failHandler;
    }

    //endregion

    //region General methods

    public void onSuccess(String result) {
        successHandler.onSuccess(result);
    }

    public void onFail(String errorMessage) {
        failHandler.onFail(errorMessage);
    }

    //endregion

    //region Interfaces

    public interface SuccessHandler {

        void onSuccess(String result);

    }

    public interface FailHandler {

        void onFail(String errorMessage);

    }

    //endregion

}
