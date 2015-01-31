package com.example.redmart.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.ErrorHandler;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.redmart.network.NetworkUtils;

public abstract class BaseQuery {
	private static final String TAG = "BaseQuery";
	public static final String NET_LOG = "NET_LOG";
	
	protected String mRequestUrl;
	protected LinkedHashMap<String, String> mParameters;
	protected HashMap<String, String> mHeaders;
	protected String mPostText = null;
	protected int mRequestMethod;
	protected ErrorHandler errorHandler = null;
	protected Bundle mParams;
	protected boolean useToken = true;
	
	public BaseQuery()
	{	
	}
	/**
	 * Should be used only for DisqusApi as of March 10, 2014
	 * @param context
	 * @param request
	 * @param url
	 * @param params
	 * @param requestMethod
	 * @throws Exception
	 */
	public BaseQuery(Context context, String request, String url, int requestMethod ) throws Exception{
		mRequestUrl = url;
		mParams = new Bundle();
		mRequestMethod = requestMethod;
		mParameters = getHttpParameters(context);
		mHeaders = getHttpHeaders(context);
	}
	
	public BaseQuery(Context context, String request, Bundle params) throws Exception{
		mRequestUrl = getRequestUrlFromRequest(request, params);
		mParams = params;
		mParameters = getHttpParametersFromBundleParams(context);
		mHeaders = getHttpHeaders(context);
	}

	public BaseQuery(Context context, String request, Bundle params, int requestMethod) throws Exception{
		this(context, request, params);
		mRequestMethod = requestMethod;
		handleParamsBaseOnRequestMethod();
	}

	public BaseQuery(Context context, String request, Bundle params, int requestMethod, String postText) throws Exception{
		this(context, request, params);
		mRequestMethod = requestMethod;
		mPostText = postText;
		handleParamsBaseOnRequestMethod();
	}
	
	public void reinitiateQuery(Context context) throws Exception{
        mParameters = getHttpParametersFromBundleParams(context);
        mHeaders = getHttpHeaders(context);
        handleParamsBaseOnRequestMethod();
    }
	
	public void reinitiateQueryNoToken(Context context, String requestUrl) throws Exception{
        useToken =false;
        mRequestUrl = requestUrl;
		mParameters = getHttpParametersFromBundleParams(context);
        mHeaders = getHttpHeaders(context);
        handleParamsBaseOnRequestMethod();
    }
	
	
	protected void handleParamsBaseOnRequestMethod() throws UnsupportedEncodingException {
		switch(mRequestMethod) {
		case NetworkUtils.METHOD_GET:
		case NetworkUtils.METHOD_DELETE:
		case NetworkUtils.METHOD_PUT:
		case NetworkUtils.METHOD_POST:
			break;
		}
	}

	public String constructFullUrlForGet(Context context){
		String url = mRequestUrl;
		Iterator<String> iter = mParameters.keySet().iterator();
		String key;
		while(iter.hasNext()) {
			key = iter.next();
			if (url.contains("?")) {
				url += "&" + key + "=" + mParameters.get(key);
			}	
			else {
				url += "?" + key + "=" + mParameters.get(key);
			}
		}
	    return url;
	}
		
	
	public String getRequestUrl(){
		return mRequestUrl;
	}

	public void setRequestUrl(String requestUrl){
		mRequestUrl = requestUrl;
	}
	
	public int getRequestMethod() {
		return mRequestMethod;
	}

	public void setRequestMethod(int requestMethod) {
		 mRequestMethod = requestMethod;
	}
	
	public ErrorHandler getErrorHandler(){
		return errorHandler;
	}

	public void setErrorHandler(ErrorHandler errorHandler){
		this.errorHandler = errorHandler;
	}

	public LinkedHashMap<String, String> getParameters(){
		return mParameters;
	}
	
	public void setUseToken(boolean useToken){
		this.useToken = useToken;
	}
	
	public HashMap<String, String> getHeaders(){
		return mHeaders;
	}

	protected String getPostText() {
		return mPostText;
	}

    protected JSONObject getJSONPostText() throws JSONException {
        if (!TextUtils.isEmpty(mPostText)){
            return new JSONObject(mPostText);
        }
        else{
            return null;
        }
    }

	protected abstract String getRequestUrlFromRequest(String request, Bundle params) throws Exception;

	protected LinkedHashMap<String, String> getHttpParameters(Context context) throws Exception{
		String key;
		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		Iterator<String> iter = mParams.keySet().iterator();
		while(iter.hasNext()){
			key = iter.next();
			parameters.put(key, mParams.get(key).toString());
		}
		
		return parameters;
	}
	
	private LinkedHashMap<String, String> getHttpParametersFromBundleParams(Context context) throws Exception{
		LinkedHashMap<String, String> parameters = getHttpParameters( context );
		return parameters;
	}
	
	protected HashMap<String, String> getHttpHeaders(Context context) throws Exception{
		HashMap<String, String> headers = new HashMap<String, String>();
		return headers;
	}
	
	@Override
	public String toString()
	{
		return "Url <" + mRequestUrl + "> Headers <" + mHeaders + "> Parameters <" + mParameters + ">" + " Post <" + mPostText + ">";
	}
}