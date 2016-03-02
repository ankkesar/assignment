package com.example.android.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.recyclerview.modals.SubContent;
import com.squareup.picasso.Picasso;

/**
 * Created by ankur on 28-02-2016.
 *
 */
public class CustomInnerAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    SubContent[] mDataSet;
     public CustomInnerAdapter1(SubContent[] data){
         mDataSet=data;
     }


    @Override
    public int getItemCount() {
        return mDataSet.length;
    }


    RecyclerView.ViewHolder viewholder;
    View view;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.


                view=LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.product_list_layout1, viewGroup, false);
        RecyclerView.LayoutParams lp= (RecyclerView.LayoutParams) view.getLayoutParams();
        lp.width=view.getResources().getDisplayMetrics().widthPixels;
        lp.height=view.getResources().getDisplayMetrics().heightPixels/6;
        view.setLayoutParams(lp);
                viewholder = new Product_List_Holder(view);



        return viewholder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

                Product_List_Holder viewholder2 = new Product_List_Holder(view);
        viewholder2.labl.setText(mDataSet[position].getLabel());
        Picasso.with(viewholder2.labl.getContext()).load(mDataSet[position].getImage()).into(viewholder2.imag);

    }


    private static class Product_List_Holder extends RecyclerView.ViewHolder {
        ImageView imag;
        TextView labl;

        public ImageView getImag() {
            return imag;
        }

        public void setImag(ImageView imag) {
            this.imag = imag;
        }

        public TextView getLabl() {
            return labl;
        }

        public void setLabl(TextView labl) {
            this.labl = labl;
        }

        public Product_List_Holder(View view) {
            super(view);
           imag= (ImageView) view.findViewById(R.id.caru_img);
            labl= (TextView) view.findViewById(R.id.caru_label);

        }
    }

}
