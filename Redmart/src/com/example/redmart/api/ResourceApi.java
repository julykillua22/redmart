package com.example.redmart.api;

import android.content.Context;
import android.os.Bundle;

import com.example.redmart.network.NetworkUtils;

public class ResourceApi extends BaseApi {
    private static final String TAG = "ResourceApi";
    public static class Query extends BaseQuery{

        private static final String TAG = "ResourceApi.Query";

        private Query(Context context, String request, Bundle params, int requestMethod) throws Exception{
            super(context, request, params, requestMethod);
        }

        public static Query prepareQuery(Context context, String request, Bundle params, int requestMethod) throws Exception {
            return new Query(context, request, params, requestMethod);
        }
        
        public static final String RESOURCE_LIST_REQUEST = "resource_list_request";
        private static final String RESOURCE_LIST_URL = "https://api.redmart.com/v1.5/products/bytype/?uri=chinese-green&page=null&pageSize=20&sort=null&instock=false";
          
        @Override
        protected String getRequestUrlFromRequest(String request, Bundle params)
                throws Exception {
            String requestUrl = null;
            if (request.equals(RESOURCE_LIST_REQUEST))
            {
                requestUrl = RESOURCE_LIST_URL;
            }
            if (requestUrl == null){
                throw new Exception();
            }
            return requestUrl;
        }
    }
    
    public static Query getList(Context context) throws Exception
    {
    	Bundle params = new Bundle();
        return ResourceApi.Query.prepareQuery(context, Query.RESOURCE_LIST_REQUEST, params, NetworkUtils.METHOD_GET);
    }
}