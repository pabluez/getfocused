package com.android.iFocus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.insightoverflow.iFocus.R;

public class AboutActivity extends Activity implements OnClickListener {
	Button buttonAbout = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {          

        super.onCreate(savedInstanceState);    
        setContentView(R.layout.about_view);

        buttonAbout = (Button)findViewById(R.id.buttonAbout);
        buttonAbout.setOnClickListener(this);
    }

	@Override
	public void onClick(View buttonAbout) {
		Intent o = new Intent(AboutActivity.this, MethodActivity.class);
    	startActivity(o);
	}

}
