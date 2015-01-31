package com.example.redmart.beans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Resource implements Parcelable {

	private String name;
	private String desc;
	private double price;
	private String image;
	
	public Resource( JSONObject json )
	{
		try
		{	
			name = json.has( "title" ) ? json.getString( "title" ) : "";
			desc = json.has( "desc" ) ? json.getString( "desc" ) : "";
			if( json.has( "img" ) )
			{
				image = "http://media.redmart.com/newmedia/200p" + json.getJSONObject( "img" ).getString( "name" );
			}
			if( json.has( "pricing" ) )
			{
				price = json.getJSONObject( "pricing" ).getDouble( "price" );
			}
		}
		catch( JSONException e )
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
    
    @Override
    public void writeToParcel(Parcel out, int i) 
    {
    	out.writeString(name);
    	out.writeString(image);
    	out.writeString(desc);
    	out.writeDouble(price);
    }
    
    public Resource(Parcel in)
    {
    	name = in.readString();
    	image = in.readString();
    	desc = in.readString();
    	price = in.readDouble();
    }
    
    public static final Parcelable.Creator<Resource> CREATOR = new Parcelable.Creator<Resource>() {
        public Resource createFromParcel(Parcel in) {
            return new Resource(in);
        }

        public Resource[] newArray(int size) {
            return new Resource[size];
        }
    };
	
	public String getName()
	{
		return name;
	}
	
	public String getDesc()
	{
		return desc;
	}

	public double getPrice()
	{
		return price;
	}
	
	public String getImage()
	{
		return image;
	}
	
	public static ArrayList<Resource> toArrayList( JSONArray array )
	{
		ArrayList<Resource> details = new ArrayList<Resource>();
		try
		{
			for( int i = 0; i < array.length(); i++ )
			{
				Resource detail = new Resource( array.getJSONObject( i ) );
				details.add( detail );
			}
		}
		catch( JSONException e )
		{
			e.printStackTrace();
		}
		return details;
	}
}