package com.trajeledbt;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by root on 12/06/17.
 */


public class ConnectThread extends Thread {

    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    public ConnectThread(BluetoothSocket socket) {

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {

    }

    /* Call this from the main activity to send data to the remote device */
    public void send(String send) {
        byte[] buffer = send.getBytes();
        try {
            mmOutStream.write(buffer);
        } catch (IOException e) { }
    }

}
