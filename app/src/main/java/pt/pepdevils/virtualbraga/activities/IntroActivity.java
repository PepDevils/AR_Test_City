package pt.pepdevils.virtualbraga.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import pt.pepdevils.virtualbraga.R;


public class IntroActivity extends AppCompatActivity {

    final static int CALLBACK_NUMBER = 21;
    final static String TAG = "IntroActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        //RUN TIME PERMISSION FOR CAMERA
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED && permissionCheck2 == PackageManager.PERMISSION_GRANTED) {
                //PERMISSION GRANTED
                StartDroidAr();
            } else {
                //PERMISSION not GRANTED
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION},CALLBACK_NUMBER);
            }
        } else {
            StartDroidAr();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CALLBACK_NUMBER){
            Log.i(TAG, "PERMISSION FOR CAMERA AND LOCATION GRANTED");
            StartDroidAr();
        }else{
            Log.i(TAG, "PERMISSION NOT GRANTED");
            //todo: criar um pop up para e fechar a aplica��o...
        }
    }

    
    private void StartDroidAr(){

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
