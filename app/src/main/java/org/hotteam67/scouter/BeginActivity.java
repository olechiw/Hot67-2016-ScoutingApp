package org.hotteam67.scouter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.io.InputStream;
import java.io.OutputStream;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothAdapter;
import android.os.ParcelUuid;
import java.io.IOException;
import java.util.Set;
import java.util.List;
import android.widget.RadioButton;

public class BeginActivity extends AppCompatActivity {

    Toolbar toolbar;

    ToggleBox sallyPort;
    ToggleBox drawbridge;
    ToggleBox porticullis;
    ToggleBox cheval;
    ToggleBox ramparts;
    ToggleBox moat;
    ToggleBox rockWall;
    ToggleBox roughTerrain;

    ToggleBox[] obstacles = new ToggleBox[8];

    EditText lowBarAuton;
    EditText lowBarTeleop;
    EditText sallyPortAuton;
    EditText sallyPortTeleop;
    EditText drawbridgeAuton;
    EditText drawbridgeTeleop;
    EditText porticullisAuton;
    EditText porticullisTeleop;
    EditText chevalAuton;
    EditText chevalTeleop;
    EditText rockWallAuton;
    EditText rockWallTeleop;
    EditText roughTerrainAuton;
    EditText roughtTerrainTeleop;
    EditText moatAuton;
    EditText moatTeleop;
    EditText rampartsAuton;
    EditText rampartsTeleop;

    EditText teamNumber;

    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        InitializeComponents();

        InitBluetooth();
    }

    private void InitializeComponents()
    {
        sallyPort = (ToggleBox) findViewById(R.id.sallyPort);
        drawbridge = (ToggleBox) findViewById(R.id.drawbridge);
        porticullis = (ToggleBox) findViewById(R.id.porticullis);
        cheval = (ToggleBox) findViewById(R.id.cheval);
        ramparts = (ToggleBox) findViewById(R.id.ramparts);
        moat = (ToggleBox) findViewById(R.id.moat);
        rockWall = (ToggleBox) findViewById(R.id.rockWall);
        roughTerrain = (ToggleBox) findViewById(R.id.roughTerrain);
        ToggleBox[] obstacle = {
                roughTerrain,
                sallyPort,
                drawbridge,
                porticullis,
                cheval,
                ramparts,
                moat,
                rockWall
        };
        obstacles = obstacle;

        lowBarAuton = (EditText) findViewById(R.id.lowBarAuton);
        lowBarTeleop = (EditText) findViewById(R.id.lowBarTeleop);
        sallyPortAuton = (EditText) findViewById(R.id.sallyTeleop);
        sallyPortTeleop = (EditText) findViewById(R.id.sallyAuton);
        drawbridgeAuton = (EditText) findViewById(R.id.drawbridgeAuton);
        drawbridgeTeleop = (EditText) findViewById(R.id.drawbridgeTeleop);
        porticullisAuton = (EditText) findViewById(R.id.portAuton);
        porticullisTeleop = (EditText) findViewById(R.id.portTeleop);
        chevalAuton = (EditText) findViewById(R.id.chevalAuton);
        chevalTeleop = (EditText) findViewById(R.id.chevalTeleop);
        rockWallAuton = (EditText) findViewById(R.id.rockWallAuton);
        rockWallTeleop = (EditText) findViewById(R.id.rockWallTeleop);
        roughTerrainAuton = (EditText) findViewById(R.id.roughTerrainAuton);
        roughtTerrainTeleop = (EditText) findViewById(R.id.roughTerrainTeleop);
        moatAuton = (EditText) findViewById(R.id.moatAuton);
        moatTeleop = (EditText) findViewById(R.id.moatTeleop);
        rampartsAuton = (EditText) findViewById(R.id.rampartsAuton);
        rampartsTeleop = (EditText) findViewById(R.id.rampartsTeleop);

        teamNumber = (EditText) findViewById(R.id.teamNumberControl);

        doneButton = (Button) findViewById(R.id.save);
    }

    public void DoneClick(View view)
    {
        int obstaclesChecked = 0;
        for (ToggleBox t : obstacles) {
            if (t.isChecked())
            {
                obstaclesChecked++;
            }
        }

        if (obstaclesChecked != 4) {
            MessageDialog("Not enough obstacles", "You have not provided at " +
                    "least four present obstacles");
            return;
        }


        int num = Integer.parseInt(teamNumber.getText().toString());

        if (num<0)
        {
            MessageDialog("Invalid Team number", "The team number provided" +
                    "is either 0 or less than that, which is invalid.");
            return;
        }

        SaveTeam(num);
    }

    public void SaveTeam(int teamNumber)
    {
    }

    public void MessageDialog(String title, String text)
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(text);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dlgAlert.create().show();
    }

    OutputStream outputStream;
    InputStream inStream;
    private void InitBluetooth() {

        try {
            BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
            if (blueAdapter != null) {
                if (blueAdapter.isEnabled()) {
                    Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                    if (bondedDevices.size() > 0) {
                        BluetoothDevice[] devices = (BluetoothDevice[]) bondedDevices.toArray();
                        BluetoothDevice device = devices[0];
                        ParcelUuid[] uuids = device.getUuids();
                        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                        socket.connect();
                        outputStream = socket.getOutputStream();
                        inStream = socket.getInputStream();
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}