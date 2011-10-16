package com.android.iFocus;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
    MediaPlayer mediaPlayer = null;
    ToggleButton toggleRain = null;
    Button buttonAbout = null;
    Button buttonMethod = null;
    Button buttonLink = null;
    
    
    public boolean isOnline() {
    	//Check if internet is connected
    	ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    	return activeNetworkInfo != null;
  
    }
    
    public void prepareStream(){
    	if(isOnline()){
    		// init player
        	try {
            	mediaPlayer = MediaPlayer.create(this, Uri.parse("http://vprbbc.streamguys.net:80/vprbbc24.mp3"));
            	x=2;
           //if any exception occur	
            } catch(Exception e){
            	//x=3 means that "notconnectedbutton" is gonna be shown
            	x=3;
            }
    	} else {
    		x=3;
    	}
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
        
        
        if (!isOnline()){
        	toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.notconnectedbutton));
        	x=3;
        }
     
    }
    
    
        public void onClick(View v) {
        
        	
            if( toggleRain.getId() == ((Button)v).getId() ){
        
            	if (((CompoundButton) toggleRain).isChecked()) {
                    toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.stopbutton));
                } else if (x==3){
                    toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.notconnectedbutton));
                } else {
                	toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.playbutton));
                }

            	if (x==3){
            		if (isOnline()){
            			if(count==0){
                			
                			prepareStream();
                			mediaPlayer.start();
                			count = 1;
                		} else {
                			mediaPlayer.pause();
                			count = 0;
                		}
            			
            		}
            	} else {
            		if(count==0){
            			
            			prepareStream();
            			mediaPlayer.start();
            			count = 1;
            		} else {
            			mediaPlayer.pause();
            			count = 0;
            		}

            	}
        		
            	
            }
            else if( buttonAbout.getId() == ((Button)v).getId() ){

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