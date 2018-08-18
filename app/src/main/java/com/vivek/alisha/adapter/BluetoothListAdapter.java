package com.vivek.alisha.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.alisha.R;
import com.vivek.alisha.interfaces.bluetooth.BluetoothClickListener;
import com.vivek.alisha.model.BluetoothModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vivek on 01-01-2018.
 */

public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothListAdapter.BluetoothListHolder>{

    Context context;
    ArrayList<BluetoothModel> bluetoothModelArrayList;
    BluetoothClickListener bluetoothClickListener;

    public BluetoothListAdapter(Context context,BluetoothClickListener bluetoothClickListener, ArrayList<BluetoothModel> bluetoothModelArrayList) {
        this.context = context;
        this.bluetoothClickListener = bluetoothClickListener;
        this.bluetoothModelArrayList = bluetoothModelArrayList;
    }

    @Override
    public BluetoothListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_bluetooth_list, parent, false);

        return new BluetoothListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BluetoothListHolder holder, int position) {

        holder.rowTxtBluetooth.setText(bluetoothModelArrayList.get(position).bluetoothDevice.getName());
        holder.rowTxtBluetooth.setId(position);
        holder.rowTxtBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothClickListener.getBluetoothAdapterClick(view.getId(),bluetoothModelArrayList.get(view.getId()).bluetoothDevice);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bluetoothModelArrayList.size();
    }

    class BluetoothListHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.rowTxtBluetooth)
        TextView rowTxtBluetooth;
        public BluetoothListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
