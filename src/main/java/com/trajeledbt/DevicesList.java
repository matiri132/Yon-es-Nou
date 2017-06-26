package com.trajeledbt;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class DevicesList extends ListActivity {

    static String MAC_ADRESS = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        if (BluetoothAdapter.getDefaultAdapter().getBondedDevices().size() > 0) {
            for (BluetoothDevice device : BluetoothAdapter.getDefaultAdapter().getBondedDevices()) {
                ArrayBluetooth.add(device.getName());

            }
        }

        setListAdapter(ArrayBluetooth);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String generalinfo = ((TextView) v).getText().toString();

        for (BluetoothDevice device : BluetoothAdapter.getDefaultAdapter().getBondedDevices()) {
            if (device.getName().equals(generalinfo)) {

                Intent macreturn = new Intent();
                macreturn.putExtra(MAC_ADRESS, device.getAddress());
                setResult(RESULT_OK, macreturn);
                finish();
            }
        }
    }
}
