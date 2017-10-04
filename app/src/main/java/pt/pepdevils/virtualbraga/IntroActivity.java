package pt.pepdevils.virtualbraga;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.IOException;


public class IntroActivity extends AppCompatActivity {

    private static final int CALLBACK_NUMBER = 21;
    private ArchitectView architectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        //RUN TIME PERMISSION FOR CAMERA
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                //PERMISSION GRANTED
                WikitudeViewAndConfig();
            } else {
                //PERMISSION not GRANTED
                requestPermissions(new String[]{Manifest.permission.CAMERA},CALLBACK_NUMBER);
            }
        } else {
            WikitudeViewAndConfig();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        this.architectView.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CALLBACK_NUMBER){
            Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "PERMISSION NOT", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();
        try {
            architectView.load(WikitudeHelper.URL);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        architectView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();
    }

    private void WikitudeViewAndConfig() {
        this.architectView = (ArchitectView) findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey(WikitudeHelper.WIKITUDE_LICENSE);
        this.architectView.onCreate(config);
    }
}
