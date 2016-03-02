/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.recyclerview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.recyclerview.modals.AbstractBaseContent;
import com.example.android.recyclerview.modals.Content;
import com.example.android.recyclerview.modals.SubContent;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager} and a
 * {@link GridLayoutManager}.
 */
public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

new FetchAsync().execute(rootView.getContext());

        return rootView;
    }

    private AbstractBaseContent[] parseJSON(InputStream ins) {
        BufferedReader streamReader = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        try {
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractBaseContent dataset[] = null;
        try {
            JSONArray dataObj = new JSONArray(responseStrBuilder.toString());
            if (dataObj != null && dataObj.length() > 0) {
                dataset = new AbstractBaseContent[dataObj.length()];

                for (int i = 0; i < dataObj.length(); i++) {
                    Content c = new Content();
                    c.setImage(dataObj.getJSONObject(i).getString("image"));
                    c.setLabel(dataObj.getJSONObject(i).getString("label"));
                    c.setTemplate(dataObj.getJSONObject(i).getString("template"));
                    JSONArray subContArray = dataObj.getJSONObject(i).getJSONArray("items");
                    SubContent[] subcontent = null;
                    if (subContArray != null && subContArray.length() > 0) {
                        subcontent = new SubContent[subContArray.length()];
                        for (int j = 0; j < subContArray.length(); j++) {
                            subcontent[j] = new SubContent();
                            subcontent[j].setLabel(subContArray.getJSONObject(j).getString("label"));
                            if (subContArray.getJSONObject(j).has("image"))
                                subcontent[j].setImage(subContArray.getJSONObject(j).getString("image"));
                            else
                                subcontent[j].setImage(subContArray.getJSONObject(j).getString("image_url"));

                            subcontent[j].setWeb_url(subContArray.getJSONObject(j).getString("web-url"));
                        }
                    }
                    c.setSubContent(subcontent);

//                if(dataObj.getJSONObject(i).getString("template").equals(Content.TEMPLATE_TYPE_1)){
//
//
//                } else if(dataObj.getJSONObject(i).getString("template").equals(Content.TEMPLATE_TYPE_2)){
//
//                } else if(dataObj.getJSONObject(i).getString("template").equals(Content.TEMPLATE_TYPE_3)){
//
//                }
                    dataset[i] = c;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataset;
    }
class FetchAsync extends AsyncTask<Context, Void, AbstractBaseContent[]> {

    @Override
    protected AbstractBaseContent[] doInBackground(Context... voids) {
        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier("f_one",
                        "raw", voids[0].getPackageName()));


        return parseJSON(ins);
    }

    @Override
    protected void onPostExecute(AbstractBaseContent[] aVoid) {
        super.onPostExecute(aVoid);
        if(aVoid!=null){
            mAdapter = new CustomAdapter(aVoid);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
}
