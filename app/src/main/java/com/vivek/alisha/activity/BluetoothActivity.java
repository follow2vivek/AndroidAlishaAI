package com.vivek.alisha.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vivek.alisha.R;
import com.vivek.alisha.adapter.BluetoothListAdapter;
import com.vivek.alisha.interfaces.bluetooth.BluetoothClickListener;
import com.vivek.alisha.model.BluetoothModel;
import com.vivek.alisha.utils.Global;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BluetoothActivity extends BaseActivity implements BluetoothClickListener {

    @BindView(R.id.recyclerBluetooth)
    RecyclerView recyclerBluetooth;
    LinearLayoutManager linearLayoutManager;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    private ProgressDialog progressDialog;
    final int BT_CODE = 1;
    ArrayList<BluetoothModel> bluetoothModelArrayList = new ArrayList<>();

    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerBluetooth.setLayoutManager(linearLayoutManager);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (myBluetooth == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            finish();
        } else if (!myBluetooth.isEnabled()) {
            Intent intentBtOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intentBtOn, BT_CODE);
        }

        pairedDeviceList();
    }

    private void pairedDeviceList() {

        pairedDevices = myBluetooth.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                bluetoothModelArrayList.add(new BluetoothModel(bt));
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        BluetoothListAdapter bluetoothListAdapter = new BluetoothListAdapter(this, this, bluetoothModelArrayList);
        recyclerBluetooth.setAdapter(bluetoothListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void getBluetoothAdapterClick(int pos, BluetoothDevice bluetoothDevice) {

        new ConnectToBt().execute(bluetoothDevice);
    }

    class ConnectToBt extends AsyncTask<BluetoothDevice, Void, Void> {

        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(BluetoothActivity.this, "Connecting...", "Please wait!!!");
            progressDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(BluetoothDevice... bluetoothDevices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(bluetoothDevices[0].getAddress());//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (!ConnectSuccess) {
                Global.showToast(BluetoothActivity.this, "Connection Failed");
                finish();
            } else {
                Global.showToast(BluetoothActivity.this, "Connected");
                isBtConnected = true;

            }
            progressDialog.dismiss();
            try {
                btSocket.getOutputStream().write("0".toString().getBytes());
            } catch (IOException e) {
                Global.showToast(BluetoothActivity.this, e.getMessage());
            }
        }
    }

}
