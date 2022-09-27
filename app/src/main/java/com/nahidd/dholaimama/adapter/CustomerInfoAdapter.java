package com.nahidd.dholaimama.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nahidd.dholaimama.R;
import com.nahidd.dholaimama.model.CustomerInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerInfoAdapter extends RecyclerView.Adapter<CustomerInfoAdapter.ViewHolder> {


    private List<CustomerInfo> customerInfoList;
    private Context context;

    public CustomerInfoAdapter(List<CustomerInfo> customerInfoList, Context context) {
        this.customerInfoList = customerInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.iten_view_customer_list,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomerInfoAdapter.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return customerInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView customer_Image;
        //private TextView customer_name,customer_address,cus
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
