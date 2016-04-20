package com.dpazolopez.domotica20;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Valor añadido";
    Camera cam;
    boolean isFlashOn=false;
    boolean isVib=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void Vibracion(View v) {
        Button IO = (Button) findViewById(R.id.Vibracion);
        Vibrator vib = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        if (!isVib) {
            vib.vibrate(10000);
            IO.setText("Apagar vibracion");
            isVib=true;
        }else {
            vib.cancel();
            IO.setText("Encender vibracion");
            isVib=false;
        }
    }
    public void Luz (View w){
        Button IO = (Button) findViewById(R.id.onoff);
        if (IO.getText().equals(getText(R.string.on))) {
            IO.setText(getText(R.string.off));
            Toast.makeText(MainActivity.this, "Has cambiado la luz a " + getText(R.string.off), Toast.LENGTH_SHORT).show();
        }
        else
            IO.setText(getText(R.string.on));
        Toast.makeText(MainActivity.this, "Has cambiado la luz a " + getText(R.string.on), Toast.LENGTH_SHORT).show();
    }
    public void Flash(View v){
        try {
            Button IO = (Button) findViewById(R.id.Linterna);
            if (!isFlashOn){
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    cam = Camera.open();
                    Camera.Parameters p = cam.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    cam.setParameters(p);
                    cam.startPreview();
                    isFlashOn=true;
                    IO.setText("Apagar Linterna");
                }
            } else {
                cam.stopPreview();
                cam.release();
                isFlashOn=false;
                IO.setText("Encender Linterna");
            }

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception flashLightOn()",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void llama(View v) {
        EditText editText = (EditText) findViewById(R.id.callField);
        String message = editText.getText().toString();
        Uri number = Uri.parse("tel:".concat(message));
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }
    public void navegador(View v) {
        EditText editText = (EditText) findViewById(R.id.navegador);
        String message = editText.getText().toString();
        Uri webpage = Uri.parse(message);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
    public void mapas(View v) {
        EditText editText = (EditText) findViewById(R.id.btmaps);
        String message = editText.getText().toString();
        // Map point based on address
        Uri location = Uri.parse("geo:0,0?q=" + message);
        // Or map point based on latitude/longitude
        // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }
    public void sendMessage(View v) {
        Intent intent = new Intent(this, DisplayMessage.class);
        EditText et = (EditText) findViewById(R.id.texto);
        String msg = et.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, msg);
        startActivity(intent);
    }
    public void forResult(View v) {
        Intent intent1 = new Intent(this, Activity2.class);
        startActivityForResult(intent1, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("resultado");
                TextView tv = (TextView) findViewById(R.id.texto1);
                tv.setText(result);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
