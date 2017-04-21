package com.example.zwy.patchupdatedemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zwy.patchupdatedemo.utils.PatchUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private final String PATCH_NAME = "update.patch";
    private final String NEW_APK_NAME = "new_version.apk";
    private String newApkPath;
    private String patchPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPath();

    }


    private void initPath(){
        newApkPath =getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + NEW_APK_NAME;
        patchPath = getCacheDir().getAbsolutePath() + File.separator + PATCH_NAME;
    }

    public void checkAndUpdate(View view) {
        if (isHasNewVersion()) {
            downloadAndPatch();
        } else {
            Toast.makeText(this, "have no new version", Toast.LENGTH_SHORT).show();
        }

    }


    public void testFunction(View view) {
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(getPackageName(), Context.MODE_PRIVATE);
            if (packageInfo != null) {
                Toast.makeText(this, String.format("current version %s", packageInfo.versionName), Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //把差分包放在SD卡中模拟是否有新版本
    private boolean isHasNewVersion() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + PATCH_NAME);
        if (file.exists()) {
            return true;
        }
        return false;

    }

    //模拟下载到缓存目录
    private void downloadAndPatch() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + PATCH_NAME);
                    FileInputStream inputStream = new FileInputStream(file);
                    FileOutputStream fileOutputStream = new FileOutputStream(patchPath);
                    int len = -1;
                    byte[] buffer = new byte[1024];
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    inputStream.close();
                    fileOutputStream.close();
                    ApplicationInfo applicationInfo = getApplicationInfo();
                    PatchUtils.patch(applicationInfo.sourceDir, newApkPath, patchPath);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(newApkPath)),
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }
        }.execute();
    }


}
