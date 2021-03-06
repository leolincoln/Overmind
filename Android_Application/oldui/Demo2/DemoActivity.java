package overmind.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class DemoActivity extends Activity {
	private Socket socket;
	private Button leftButton;
	private Button rightButton;
	private Button fullScreen;
	private Button close;
	private Button connect;
	private Button qr;
	private EditText ip;
	private EditText read;
	//private DataInputStream fromServer;
	private DataOutputStream toServer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        leftButton = (Button) findViewById(R.id.button1);
        rightButton = (Button) findViewById(R.id.button2);
        fullScreen = (Button) findViewById(R.id.button3);
        connect = (Button) findViewById(R.id.button4);
        close = (Button) findViewById(R.id.button5);
        ip = (EditText) findViewById(R.id.editText1);
        read = (EditText) findViewById(R.id.editText2);
        qr = (Button) findViewById(R.id.button6);
    }
    //called when scan button is called, calls the zxing application
    public void qrread(View view)
    {
    	IntentIntegrator integrator = new IntentIntegrator(DemoActivity.this);
    	integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
    }
    //handles the message from zxing
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	  if (scanResult != null) 
    	  {
    		  String contents = scanResult.getContents();
    		  Toast.makeText(this, contents, Toast.LENGTH_LONG).show();
    	  }
    	  else
    	  {
    		  Toast.makeText(this, "something went wrong there pall", Toast.LENGTH_LONG).show();
    	  }
    	} 
    //sends the message to progress slideshow left
    public void left(View view)
    {
    	try
    	{
    		toServer.writeBytes("PREV");
    		toServer.flush();
    	}
    	catch(IOException ex)
    	{
    		Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
    	}
    }
        //sends the message to progress slideshow right
    public void right(View view)
    {
    	try
    	{
    		toServer.writeBytes("NEXT");
    		toServer.flush();
    	}
    	catch(IOException ex)
    	{
    		Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
    	}
    }
        //sends the message to fullscreen
    public void fullscreen(View view)
    {
    	try
    	{
    		toServer.writeBytes("FULL");
    		toServer.flush();
    	}
    	catch(IOException ex)
    	{
    		Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
    	}
    }
        //sends the message to close slideshow
    public void close(View view)
    {
    	try
    	{
    		toServer.writeBytes("SHUT");
    		toServer.flush();
    	}
    	catch(IOException ex)
    	{
    		Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
    	}
    }
        //sends the message to connect to specified server
    public void connect(View view)
    {
    	try
        {
    		String iptext = ip.getText().toString();
        	socket = new Socket( iptext, 8080 );
        	
    		//ip.setText("128.113.247.240");
    		//socket = new Socket ("128.113.247.240",8080);
            //fromServer = new DataInputStream( socket.getInputStream() );
            toServer = new DataOutputStream( socket.getOutputStream() );
            Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show();
        }
        catch(IOException ex)
        {
        	Toast.makeText(this, "Failed ", Toast.LENGTH_LONG).show();
        	Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    	
    }
}
