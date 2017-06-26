package com.trajeledbt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainMenu extends AppCompatActivity {

    //Elementos del menu
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_CONNECT_BT = 2;

    public boolean connection = false;
    private static String MAC = null;
    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    BluetoothAdapter btadapter = null;
    BluetoothDevice btdevice = null;
    BluetoothSocket btsocket = null;
    public ConnectThread connectedThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /////////////////BLUETOOTH///////////////////////
        btadapter = BluetoothAdapter.getDefaultAdapter();

        if (btadapter == null) {
            Toast.makeText(getApplicationContext(), R.string.btavailable, Toast.LENGTH_LONG).show();

        } else if (!btadapter.isEnabled()) {
            Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBt, REQUEST_ENABLE_BT);

        }
        ////////////////////////////////////////////////////

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar (toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    Tab1 tab1 = new Tab1();
                    return tab1;
                case 1:
                    Tab2 tab2 = new Tab2();
                    return tab2;
                case 2:
                    Tab3 tab3 = new Tab3();
                    return tab3;
                case 3:
                    TabConectBt tab4 = new TabConectBt();
                    return tab4;

            }

            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Modos Basicos";
                case 1:
                    return "Mis Modos";
                case 2:
                    return "Estado";
                case 3:
                    return "Conexion";
            }
            return null;
        }
    }

    public boolean connectBluetooth() {

        if (connection) {
            //desconectar
            try {
                btsocket.close();

                connection = false;
                //butConectBt.setText(R.string.butConnect);
                Toast.makeText(getApplicationContext(), R.string.btdisconnected, Toast.LENGTH_LONG).show();
                return false;

            } catch (IOException error) {
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
            }
        } else {
            //Conectar
            Intent openlist = new Intent(MainMenu.this, DevicesList.class);
            startActivityForResult(openlist, REQUEST_CONNECT_BT);
            return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), R.string.btactivated, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), R.string.btnotactivated, Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case REQUEST_CONNECT_BT:
                if (resultCode == Activity.RESULT_OK) {
                    MAC = data.getExtras().getString(DevicesList.MAC_ADRESS);
                    btdevice = btadapter.getRemoteDevice(MAC);

                    try {
                        btsocket = btdevice.createRfcommSocketToServiceRecord(MY_UUID);
                        btsocket.connect();
                        connection = true;
                        connectedThread = new ConnectThread(btsocket);
                        connectedThread.start();

                        // butConectBt.setText(R.string.butConnectPush);
                        Toast.makeText(getApplicationContext(), R.string.btconnected, Toast.LENGTH_LONG).show();

                    } catch (IOException error) {
                        connection = false;
                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), R.string.failmac, Toast.LENGTH_LONG).show();

                }

        }
    }
}