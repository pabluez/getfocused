package com.android.iFocus;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.insightoverflow.iFocus.R;

public class iFocusActivity extends Activity implements OnClickListener {
	
	
    //Declare Controls
	public int count = 0;
	public int x = 1;
    public MediaPlayer mediaPlayer = null;
    ToggleButton toggleRain = null;
    Button buttonAbout = null;
    Button buttonMethod = null;
    Button buttonLink = null;
    public ProgressDialog progressDialog;
    public static final String TAG = "getFocused";

    
    
    public boolean isOnline() {
    	//Check if internet is connected
    	ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    	return activeNetworkInfo != null;
  
    }
    
/*    public void prepareStream(final Context context){
    	if(isOnline()){
    		// init player
    		
    		
    		new Thread() 
    		{
    			public void run() 
    			{

    				try {
    					
    					sleep(1500);
        				//progressDialog.show();
	    		    	mediaPlayer = MediaPlayer.create(context, Uri.parse("http://vprbbc.streamguys.net:80/vprbbc24.mp3"), null);
	                	x=2;
	
    				} catch (Exception e){
    		    	x=3;
    				}
    				
    			//dismiss the progressdialog   
    			progressDialog.dismiss();
    			}
    		}.start();
    		
    
    	} else {
    		x=3;
    	}
		
    }*/
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// load layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        // load controls
        toggleRain = (ToggleButton)findViewById(R.id.toggleRain);
        buttonAbout = (Button)findViewById(R.id.buttonAbout);
        buttonMethod = (Button)findViewById(R.id.buttonMethod);
        buttonLink = (Button)findViewById(R.id.buttonLink);
        
        
        //Define Listeners (click event handler)
        toggleRain.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
        buttonMethod.setOnClickListener(this);
        buttonLink.setOnClickListener(this);
        
        
        // init state for player
        count = 0;
        
        //Context APP
        //Context appContext = this.getApplicationContext();
        
        if (!isOnline()){
        	toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.notconnectedbutton));
        	x=3;
        }
     
    }
    
    
    public void onClick(View v) {
    
    	
        if( toggleRain.getId() == ((Button)v).getId() ){
    
            //meanwhile device is offline, do this
            do {
                toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.notconnectedbutton));
                try{
                	  Thread.currentThread();
					//do what you want to do before sleeping
                	  Thread.sleep(1000);//sleep for 1000 ms
                	  //do what you want to do after sleeptig
                } catch(Exception ie){}
                
                continue;
            }while (!isOnline());
            
            //If device is online, go for this
        	if (((CompoundButton) toggleRain).isChecked()) {
                toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.stopbutton));
            } else {
            	toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.playbutton));
            }

        	final Context context = v.getContext();
        	
    		if (isOnline()){
    			//If music is not playing, start music
    			if(count==0){
    				
    				Log.d(TAG, "START PROGRESS DIALOG");
    				
    				//Showing progressDialog
    				final ProgressDialog progressDialog = ProgressDialog.show(v.getContext(), "Load", "Loading...");
    				progressDialog.show();
    				
    				//progressDialog = ProgressDialog.show(, "Load", "Loading...", true, false);
    				Log.d(TAG, "END PROGRESS DIALOG");
    				Log.d(TAG, "START THREAD TO PLAY STREAM");
        			
        		
    			}
        			
        	   		new Thread() 
            		{
            			public void run() 
            			{

            				try {
            					
            					if (count==0){
            						final MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse("http://vprbbc.streamguys.net:80/vprbbc24.mp3"), null);
            	                	x=2;
            	
            	                	progressDialog.dismiss();
            	                	
            	                	Log.d(TAG, "START MEDIA PLAYER START");
            	                	mediaPlayer.start();
            	                	Log.d(TAG, "END MEDIA PLAYER START");
            	                	
            	                	count=1;
            						
            					} else {
            						mediaPlayer.pause();
            	        			count = 0;
            					}
        	    		    	
            				} catch (Exception e){
            					x=3;
            				}
            				
            			}
            		}.start();
        			
        	}         		
        	
    } else if( buttonAbout.getId() == ((Button)v).getId() ){

    	Intent i = new Intent(iFocusActivity.this, AboutActivity.class);
    	startActivity(i);
    	
    }
    
    else if ( buttonMethod.getId() == ((Button)v).getId() ){
    	
    	Intent o = new Intent(iFocusActivity.this, MethodActivity.class);
    	startActivity(o);
    }
    
    else if ( buttonLink.getId() == ((Button)v).getId() ){
    	
    	Uri uri = Uri.parse( "http://getFocused.in" );
		startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
    }

   
}
      

	@Override
    protected void onDestroy() {
    	super.onDestroy();
    	if(mediaPlayer != null) {
    		mediaPlayer.stop();
    		mediaPlayer.release();
    		mediaPlayer = null;
    	}
    	
    }


}