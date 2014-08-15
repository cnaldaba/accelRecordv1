package com.example.accelrecordv1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements SensorEventListener {
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private boolean start = false;
	
	public void clearData (View V){
		EditText editText1 = (EditText)findViewById(R.id.editText1);
		editText1.setText("");
	}
	
	public void startDAQ (View V){
		start = true;
		
		// initialize accelerometers
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		
		
	}
	
	public void onAccuaryChanged(Sensor sensor, int accuracy)
	{
		// to do, dont know what
	}
	
	public void onSensorChanged(SensorEvent event){
		//do fancy shit
		String temp;
		EditText editText1 = (EditText)findViewById(R.id.editText1);
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			
			temp = Float.toString(x) + "," + Float.toString(y) + "," + Float.toString(z);
			
			if (start){
				editText1.append(temp + "\r\n");
			}
			
		}
	}
	public void stopDAQ(View V){
		start = false;
		// turn off accelerometers
	}
	
	public void saveData(View V){
		
		 EditText temp = (EditText)findViewById(R.id.editText1);	
		 String data = temp.getText().toString();
		 String fileName;
		 
		 File folder =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		
		 Time now = new Time();
		 now.setToNow();
		 fileName = "Data " + now.format("%Y-%m-%d %H-%M-%S") + ".txt";
		 
		 File myFile = new File(folder, fileName);

		 try {
			OutputStream os = new FileOutputStream(myFile);
			
			try {
				os.write(data.getBytes());
				//message(data + "was written successfully" + myFile.getAbsolutePath());
				Toast.makeText(MainActivity.this, "GOOD", Toast.LENGTH_LONG).show();

				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		}
		
		

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}
