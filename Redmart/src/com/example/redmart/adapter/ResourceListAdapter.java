package com.example.redmart.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.redmart.R;
import com.example.redmart.api.VolleyManager;
import com.example.redmart.beans.Resource;
import com.example.redmart.utils.ConversionUtil;
import com.example.redmart.utils.ScreenUtils;

public class ResourceListAdapter extends ArrayAdapter<Resource> {

    private static final String TAG = "ResourceListAdapter";

    private ArrayList<Resource> resourceList;
    private LayoutInflater layoutInflater;
    private Activity activity;

    public ResourceListAdapter(Activity context, ArrayList<Resource> resourceList) {
        super(context, 0, resourceList);
        this.resourceList = resourceList;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        activity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
        	convertView = layoutInflater.inflate(R.layout.row_resource, null);
        }
        ViewHolder holder = new ViewHolder(convertView);
        
        Resource resource = resourceList.get(position);

        holder.title.setText(resource.getName());
        holder.subTitle.setText( getContext().getString( R.string.price, resource.getPrice() ) );
        if( holder.subTitle.getText().length() == 0 )
        {
        	holder.subTitle.setVisibility( View.GONE );
        }
        if( ScreenUtils.isPhone( activity ) )
        {
        	holder.image.setLayoutParams(new RelativeLayout.LayoutParams(ScreenUtils.getScreenWidth( activity ) / 2, ScreenUtils.getScreenWidth( activity ) / 2));
        }
        else
        {
        	holder.image.setLayoutParams(new RelativeLayout.LayoutParams(ScreenUtils.getScreenWidth( activity ) / 3, ScreenUtils.getScreenWidth( activity ) / 3));
        }
        VolleyManager.loadImage(getContext(), holder.image, resource.getImage(), R.drawable.placeholder );
	    return convertView;
    }
    
    protected static class ViewHolder{
        public TextView title;
        public TextView subTitle;
        public NetworkImageView image;

        public ViewHolder(View root){
        	title = (TextView) root.findViewById( R.id.textview_title );
        	subTitle = (TextView) root.findViewById( R.id.textview_subtitle );
        	image = (NetworkImageView) root.findViewById( R.id.imageview_image );
        }
    }
}