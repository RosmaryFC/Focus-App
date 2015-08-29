package nyc.c4q.rosmaryfc.focus_app;



import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by s3a on 8/25/15.
 */
public class TestFragmentList extends Fragment {

    private ListView app_list;
    private Button save;
    Context mContext;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v2 = inflater.inflate(R.layout.activity_app_monitor, container, false);
        app_list = (ListView) v2.findViewById(R.id.app_list);
        save = (Button) v2.findViewById(R.id.save);

        mContext = container.getContext();
        if (mContext!=null) {
            PackageManager pm = mContext.getPackageManager();
            List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            AppAdapter adapter = new AppAdapter(mContext, applicationInfos);
            app_list.setAdapter(adapter);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beginTracking = new Intent(mContext, AppTracker.class);
                mContext.startService(beginTracking);
                Intent beginListening = new Intent(mContext, AppListener.class);
              mContext.startService(beginListening);
                Toast.makeText(mContext, "Monitoring Apps", Toast.LENGTH_LONG).show();
            }
        });

        return v2;
    }
}

