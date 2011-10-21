package com.insightoverflow.getFocused_offline;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.insightoverflow.getFocused_offline.R;

public class iFocusActivity extends Activity implements OnClickListener {
	
	
    //Declare Controls
	public int count = 0;
    MediaPlayer mediaPlayer = null;
    ToggleButton toggleRain = null;
    Button buttonAbout = null;
    Button buttonMethod = null;
    
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
        
        

        // init player
        mediaPlayer = MediaPlayer.create(this, R.raw.rain);
        
        //Define Listeners (click event handler)
        toggleRain.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
        buttonMethod.setOnClickListener(this);
        
        
        // init state for player
        count = 0;
        
     
    }
    
    
        public void onClick(View v) {
        
        	
            if( toggleRain.getId() == ((Button)v).getId() ){
        
            	if (((CompoundButton) toggleRain).isChecked()) {
                    toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.stopbutton));
                } else {
                    toggleRain.setBackgroundDrawable(getResources().getDrawable(R.drawable.playbutton));
                }

        		if(count==0){
        			mediaPlayer.setLooping(true);
        			mediaPlayer.start();
        			count = 1;
        		} else {
        			mediaPlayer.pause();
        			count = 0;
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