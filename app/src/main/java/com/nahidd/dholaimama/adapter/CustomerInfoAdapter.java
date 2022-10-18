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

        View view = LayoutInflater.from(context).inflate(R.layout.customerinfo, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomerInfoAdapter.ViewHolder holder, int position) {

        CustomerInfo customerInfo = customerInfoList.get(position);

        holder.customer_address.setText(customerInfo.getCustomer_address());
        // holder.customer_id.setText(customerInfo.getCustomer_id());
        holder.customer_name.setText(customerInfo.getCustomer_name());
        holder.customer_phoneNumber.setText(customerInfo.getCustomer_phone_number());


        String lati = String.valueOf(customerInfo.getLatitude());
        String longi = String.valueOf(customerInfo.getLongitude());


        String is = String.valueOf(customerInfo.isInterested());


        holder.isInterested.setText(is);

        holder.latitude.setText(lati);
        holder.longitude.setText(longi);


    }


    @Override
    public int getItemCount() {

        return customerInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView customer_address, customer_id, customer_name, latitude, longitude, isInterested, customer_phoneNumber;


        private CircleImageView customer_Image;


        //   private TextView customer_name,customer_address,cus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customer_address = itemView.findViewById(R.id.customer_address);
            // customer_id = itemView.findViewById(R.id.customer_id);
            customer_name = itemView.findViewById(R.id.customer_name);
            latitude = itemView.findViewById(R.id.latitudeId);
            longitude = itemView.findViewById(R.id.longitudeId);
            isInterested = itemView.findViewById(R.id.interestedId);
            customer_phoneNumber = itemView.findViewById(R.id.customer_phoneNumber);


        }


    }
}
