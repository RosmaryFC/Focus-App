package nyc.c4q.rosmaryfc.focus_app;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class AppReceiver extends ResultReceiver {

    private Receiver appReceiver;

    public AppReceiver(Handler handler) {
        super(handler);
    }

    public void setAppReceiver(Receiver appReceiver) {
        this.appReceiver = appReceiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (appReceiver != null) {
            appReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
