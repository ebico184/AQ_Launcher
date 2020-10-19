package com.aq.aq_launcher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HomeActivity extends Activity {

    List<ResolveInfo> mApps;
    GridView mGrid;

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ResolveInfo info = mApps.get(i);

            //该应用的包名
            String pkg = info.activityInfo.packageName;
            //应用的主activity类
            String cls = info.activityInfo.name;

            ComponentName componet = new ComponentName(pkg, cls);

            Intent intent = new Intent();
            intent.setComponent(componet);
            startActivity(intent);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAPPs();
        setContentView(R.layout.activity_main);
        mGrid = findViewById(R.id.apps_list);
        mGrid.setAdapter(new AppsAdapter());
        mGrid.setOnItemClickListener(listener);
    }

    private void loadAPPs(){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("AQ");

        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);

    }

    public class AppsAdapter extends BaseAdapter {

        public AppsAdapter() {
        }

        @Override
        public int getCount() {
            return mApps.size();
        }

        @Override
        public Object getItem(int i) {
            return mApps.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imgView;

            if (view == null) {
                imgView  = new ImageView(HomeActivity.this);
                imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgView.setLayoutParams(new GridView.LayoutParams(200, 200));
            }else {
                imgView = (ImageView) view;
            }
            ResolveInfo info = mApps.get(i);
            imgView.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));

            return imgView;
        }
    }


}
