package com.example.redmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.redmart.api.VolleyManager;
import com.example.redmart.beans.Resource;
import com.example.redmart.utils.ScreenUtils;

public class ResourcesActivity extends ActionBarActivity implements OnClickListener {

	public static final String RESOURCE = "resource";
    public static String TAG = "ResourcesActivity";
	private NetworkImageView headerImageView;
	private TextView titleTextView;
	private TextView detailsTextView;
	private TextView priceTextView;
	private Button addtoCartButton;
	private Resource resource;
    
    public void initActionBar()
    {
    	ActionBar actionBar = getSupportActionBar();
    	actionBar.setTitle( resource.getName().toUpperCase() );
    	actionBar.setDisplayShowHomeEnabled(true);
    	actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(getResources().getDrawable(R.drawable.redmart));
        actionBar.setIcon(getResources().getDrawable(R.drawable.redmart));
        actionBar.setBackgroundDrawable( new ColorDrawable( getResources().getColor( R.color.home_red ) ) );
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resource);
        resource = getIntent().getParcelableExtra( RESOURCE );
        setView();
        initActionBar();
    }
    
    @SuppressLint("NewApi")
	private void setView()
	{
		headerImageView = (NetworkImageView) findViewById(R.id.imageview_header);
		addtoCartButton = (Button) findViewById(R.id.button_add_to_cart);
		titleTextView = (TextView) findViewById(R.id.textview_name);
		detailsTextView = (TextView) findViewById(R.id.textview_details);
		priceTextView = (TextView) findViewById(R.id.textview_price);
		
		headerImageView.setLayoutParams(new LinearLayout.LayoutParams(
    		ScreenUtils.getScreenWidth( this ), 
    		getResources().getDimensionPixelOffset( R.dimen.home_height )));
		
		VolleyManager.loadImage(this, headerImageView, resource.getImage(), R.drawable.placeholder );
	    
		titleTextView.setText( resource.getName() );
		detailsTextView.setText( resource.getDesc() );
		priceTextView.setText( getString( R.string.price, resource.getPrice() ) );
		
		addtoCartButton.setOnClickListener( this );
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
	{
		if (item.getItemId() == android.R.id.home) 
		{
            finish();
        }
        else
        {
            super.onOptionsItemSelected(item);
        }
        return true;
    }
    
	@Override
	public void onClick(View v) 
	{
		if( v == addtoCartButton )
		{	
			Toast.makeText( this, getString( R.string.added_to_cart ), Toast.LENGTH_SHORT).show();
		}
	}
}