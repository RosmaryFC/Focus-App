package nyc.c4q.rosmaryfc.focus_app;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class App {

    @DatabaseField(generatedId = true, columnName = "APP_ID")
    int appId;

    @DatabaseField(columnName = "APP_PACKAGE")
    String appPackage;

    @DatabaseField(columnName = "APP_NAME")
    String appName;

    @DatabaseField(columnName = "APP_MONITOR")
    boolean appMonitor;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean getAppMonitor() {
        return appMonitor;
    }

    public void setAppMonitor(boolean appMonitor) {
        this.appMonitor = appMonitor;
    }
}
