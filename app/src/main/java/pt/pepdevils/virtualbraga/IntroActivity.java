package pt.pepdevils.virtualbraga;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import gl.GL1Renderer;
import gl.GLFactory;
import system.ArActivity;
import system.DefaultARSetup;
import worldData.World;

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
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                //PERMISSION GRANTED
                StartDroidAr();
            } else {
                //PERMISSION not GRANTED
                requestPermissions(new String[]{Manifest.permission.CAMERA},CALLBACK_NUMBER);
            }
        } else {
            StartDroidAr();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CALLBACK_NUMBER){
            Log.i(TAG, "PERMISSION FOR CAMERA GRANTED");
            StartDroidAr();
        }else{
            Log.i(TAG, "PERMISSION FOR NOT CAMERA GRANTED");
            //todo: criar um pop up para e fechar a aplicação...
        }
    }

    private void StartDroidAr(){
        ArActivity.startWithSetup(IntroActivity.this, new DefaultARSetup() {
            @Override
            public void addObjectsTo(GL1Renderer renderer, World world, GLFactory objectFactory) {
                world.add(objectFactory.newHexGroupTest(null));
            }
        });
    }

}
