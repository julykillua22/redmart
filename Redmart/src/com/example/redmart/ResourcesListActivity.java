package com.example.redmart;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.redmart.adapter.ResourceListAdapter;
import com.example.redmart.api.ResourceApi;
import com.example.redmart.api.VolleyManager;
import com.example.redmart.beans.Resource;
import com.example.redmart.utils.ScreenUtils;

public class ResourcesListActivity extends ActionBarActivity {
    public static final String TAG = "MinistryListFragment";
    private AdapterView listView;
    private ArrayList<Resource> resourceList;
    private ProgressBar progressBar;
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	ActionBar actionBar = getSupportActionBar();
    	actionBar.setBackgroundDrawable( new ColorDrawable( getResources().getColor( R.color.home_red ) ) );
    	actionBar.setTitle( getString( R.string.app_name ).toUpperCase() );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resource_list);
		listView = (AdapterView) findViewById(R.id.listview);
        progressBar = (ProgressBar) findViewById( R.id.progress_bar );
        execute();
    }

    private void renderContent()
    {
    	ResourceListAdapter adapter = new ResourceListAdapter( this, resourceList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() 
        {
            @Override
            public void onItemClick(android.widget.AdapterView<?> adapterView, View view, int position, long id)
            {
            	Resource resource = (Resource) listView.getItemAtPosition(position);
            	Intent myIntent = new Intent( ResourcesListActivity.this, ResourcesActivity.class);
                myIntent.putExtra( ResourcesActivity.RESOURCE, resource );
				startActivity(myIntent);
            }
        });
        listView.setVisibility(View.VISIBLE);
    }
    
    public void execute() 
    {
    	try 
    	{
    		ResourceApi.Query query = ResourceApi.getList( this );
			VolleyManager.makeVolleyStringRequest( this, query, new Response.Listener<String>() 
        		{
                    @Override
                    public void onResponse(String response) 
                    {
                        try 
                        {
                        	JSONObject jsonObject = new JSONObject( response );
                        	JSONArray json = jsonObject.getJSONArray( "products" );
                            resourceList = Resource.toArrayList( json );
                            progressBar.setVisibility( View.GONE );
                            renderContent();
                        } 
                        catch (Exception e) 
                        {
                        	e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    	error.printStackTrace();
                    }
                }
            );
		}
    	catch (Exception e)
		{
			e.printStackTrace();
		}
    }
}