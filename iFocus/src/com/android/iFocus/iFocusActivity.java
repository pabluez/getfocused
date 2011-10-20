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
    
        	if (!isOnline()){
        		Log.d(TAG, "DEVICE NOT CONNECTED");
                
                
                Log.d(TAG, "Setting background notconnectedbutton");
                toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.notconnectedbutton));
                toggleRain.setChecked(false);
                
                
        	} else {
                    		
        		
	            //If device is online, go for this
	        	if (((CompoundButton) toggleRain).isChecked()) {
	                toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.stopbutton));
	            } else {
	            	toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.playbutton));
	            }

	        	final Context context = v.getContext();
        	
    
    			//If music is not playing, start music
    			if(count==0){
    				
    				Log.d(TAG, "START PROGRESS DIALOG");
    				
    				//Showing progressDialog
    				progressDialog = ProgressDialog.show(v.getContext(), "Load", "Loading...");
    				
    				
    				//progressDialog = ProgressDialog.show(, "Load", "Loading...", true, false);
    				Log.d(TAG, "END PROGRESS DIALOG");
    				
    				
    				Log.d(TAG, "START THREAD TO PLAY STREAM");
        	   		new Thread() 
            		{
            			public void run() 
            			{

            				try {
            						
            						Log.d(TAG, "START MEDIA PLAYER LOADING");
            						mediaPlayer = MediaPlayer.create(context, Uri.parse("http://vprbbc.streamguys.net:80/vprbbc24.mp3"), null);
            						Log.d(TAG, "SETTING X = 2");
            	                	x=2;
            	
            	                	Log.d(TAG, "DISMISSING PROGRESS DIALOG");
            	                	progressDialog.dismiss();
            	                	
            	                	Log.d(TAG, "START MEDIA PLAYER START");
            	                	mediaPlayer.start();
            	                	
            	                	Log.d(TAG, "Setting count = 1");
            	                	count=1;
            						
        	    		    	
            				} catch (Exception e){
            					x=3;
            					count=0;
            				}
            				
            			}
            		}.start();
        			
            		if (x==3){
            			toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.notconnectedbutton));
    	                toggleRain.setChecked(false);
            		}
        	} else {
				Log.d(TAG, "Pausing mediaplayer");
				mediaPlayer.pause();
				Log.d(TAG, "Releasing mediaplayer");
				mediaPlayer.release();
				Log.d(TAG, "setting count = 0");
    			count=0;
    			
    			mediaPlayer = null;
			}
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