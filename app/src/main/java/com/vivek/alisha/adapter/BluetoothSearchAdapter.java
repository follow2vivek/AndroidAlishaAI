package com.vivek.alisha.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vivek.alisha.R;
import com.vivek.alisha.interfaces.bluetooth.BluetoothAdapterClickListener;
import com.vivek.alisha.model.BluetoothModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vivek on 21-10-2017.
 */

public class BluetoothSearchAdapter extends RecyclerView.Adapter<BluetoothSearchAdapter.BluetoothSearchHolder> {

    List<BluetoothModel> bluetoothList;
    Context context;
    BluetoothAdapterClickListener clickListener;

    public BluetoothSearchAdapter(List<BluetoothModel> bluetoothList, Context context, BluetoothAdapterClickListener clickListener) {
        this.context = context;
        this.bluetoothList = bluetoothList;
        this.clickListener = clickListener;
    }

    @Override
    public BluetoothSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_bluetooth_list, parent, false);
        return new BluetoothSearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BluetoothSearchHolder holder, int position) {

        holder.rowTxtBluetooth.setText(bluetoothList.get(position).bluetoothDevice.getName());
        holder.rowTxtBluetooth.setTag(position);
        if (position % 2 == 0) {

            holder.rowTxtBluetooth.setBackgroundColor(ResourcesCompat
                    .getColor(context.getResources(), android.R.color.white, null));
        } else {

            holder.rowTxtBluetooth.setBackgroundColor(ResourcesCompat
                    .getColor(context.getResources(), R.color.colorLightGrey, null));
        }

        holder.rowTxtBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.getBluetoothAdapterClick(bluetoothList.get((int) view.getTag()).bluetoothDevice);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bluetoothList.size();
    }

    public class BluetoothSearchHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rowTxtBluetooth)
        TextView rowTxtBluetooth;

        public BluetoothSearchHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
