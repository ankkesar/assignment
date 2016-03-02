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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.recyclerview.modals.AbstractBaseContent;
import com.example.android.recyclerview.modals.SubContent;
import com.squareup.picasso.Picasso;

/**
 * Provide views to RecyclerView with data from mDataSet.
 * This adapter inflates 3 types of views according to different view types
 */
public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private static final int IMAGE_CAMPAIGN = 1;
    private static final int PRODUCT_LIST_HORIZONTAL = 2;
    private static final int IMAGE_OFFER = 3;
    /**
     * providing different abstractions for maintainence
     */
    private AbstractBaseContent[] mDataSet;

    public CustomAdapter(AbstractBaseContent[] dataSet) {
        mDataSet = dataSet;
    }

   RecyclerView.ViewHolder viewholder;
    View view;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        switch (viewType) {
            case IMAGE_CAMPAIGN:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.image_campaign_layout, viewGroup, false);
                RecyclerView.LayoutParams lp= (RecyclerView.LayoutParams) view.getLayoutParams();
                lp.width=view.getResources().getDisplayMetrics().widthPixels;
                lp.height=view.getResources().getDisplayMetrics().heightPixels/6;
                view.setLayoutParams(lp);
                viewholder = new Image_Campaign_Holder(view);
                break;
            case PRODUCT_LIST_HORIZONTAL:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.product_list, viewGroup, false);
                viewholder = new Product_List_Holder(view);
                break;
            case IMAGE_OFFER:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.image_offer_layout, viewGroup, false);
                viewholder = new Image_Offer_Holder(view);
                break;
        }

        return viewholder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        switch (viewHolder.getItemViewType()) {
            case IMAGE_CAMPAIGN:
                Image_Campaign_Holder viewholder1 = new Image_Campaign_Holder(view);
                viewholder1.main_label.setText(mDataSet[position].getLabel());
                viewholder1.subImage_label.setText(mDataSet[position].getSubContent()[0].getLabel());
                Picasso.with(view.getContext()).load(mDataSet[position].getImage()).into(viewholder1.main);
                Picasso.with(view.getContext()).load(mDataSet[position].getSubContent()[0].getImage()).into(viewholder1.subImage);
                break;
            case PRODUCT_LIST_HORIZONTAL:
                Product_List_Holder viewholder2 = new Product_List_Holder(view);
                viewholder2.setData(mDataSet[position].getSubContent());
                break;
            case IMAGE_OFFER:
                Image_Offer_Holder viewholder3 = new Image_Offer_Holder(view);
                viewholder3.setData(mDataSet[position].getSubContent());
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet[position].getTemplate().equals(AbstractBaseContent.TEMPLATE_TYPE_1))
            return IMAGE_CAMPAIGN;
        if (mDataSet[position].getTemplate().equals(AbstractBaseContent.TEMPLATE_TYPE_2))
            return PRODUCT_LIST_HORIZONTAL;
        if (mDataSet[position].getTemplate().equals(AbstractBaseContent.TEMPLATE_TYPE_3))
            return IMAGE_OFFER;
        return super.getItemViewType(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    private static class Image_Campaign_Holder extends RecyclerView.ViewHolder {
        ImageView main;
        TextView main_label;
        ImageView subImage;
        TextView subImage_label;

        public Image_Campaign_Holder(View itemView) {
            super(itemView);
            main = (ImageView) itemView.findViewById(R.id.main);
            main_label = (TextView) itemView.findViewById(R.id.main_label);
            subImage = (ImageView) itemView.findViewById(R.id.sub_image);
            subImage_label = (TextView) itemView.findViewById(R.id.sub_label);
        }
    }

    private static class Product_List_Holder extends RecyclerView.ViewHolder {
        RecyclerView nestedList;
        CustomInnerAdapter nestedAdapter;

        public Product_List_Holder(View view) {
            super(view);
            nestedList = (RecyclerView) view.findViewById(R.id.prod_list);
            nestedList.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
//
        }

        public void setData(SubContent[] data) {
            nestedAdapter = new CustomInnerAdapter(data);
            nestedList.setAdapter(nestedAdapter);
        }
    }

    private class Image_Offer_Holder extends RecyclerView.ViewHolder {
        RecyclerView nestedList;
        CustomInnerAdapter1 nestedAdapter;

        public Image_Offer_Holder(View view) {
            super(view);
            nestedList = (RecyclerView) view.findViewById(R.id.prod_list);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
            nestedList.setLayoutManager(layoutManager);
//
        }

        public void setData(SubContent[] data) {
            nestedAdapter = new CustomInnerAdapter1(data);
            nestedList.setAdapter(nestedAdapter);
        }
    }

}
