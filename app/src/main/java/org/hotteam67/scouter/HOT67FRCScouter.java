package org.hotteam67.scouter;

import android.os.Bundle;
import java.io.FileOutputStream;
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
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.ParcelUuid;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Set;
import java.util.List;
import android.widget.RadioButton;

public class HOT67FRCScouter extends AppCompatActivity {

    ToggleBox sallyPort;
    ToggleBox drawbridge;
    ToggleBox porticullis;
    ToggleBox cheval;
    ToggleBox ramparts;
    ToggleBox moat;
    ToggleBox rockWall;
    ToggleBox roughTerrain;

    ToggleBox autonReached;
    ToggleBox autonCrossed;
    ToggleBox isSpy;

    ToggleBox challenged;
    ToggleBox scaled;
    ToggleBox shotFromOuterWorks;
    ToggleBox shotFromBatter;

    ToggleBox[] obstacles = new ToggleBox[8];

    EditText teleopLowGoals;
    EditText teleopHighGoals;
    EditText autonLowGoals;
    EditText autonHighGoals;

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

    EditText misses;

    EditText teamNumber;
    EditText matchNumber;
    EditText extraNotes;

    Button doneButton;

    String BeginTag = "BEGIN:";
    String EndTag = ":";
    String MatchNumberTag = "MATCHNAME:";
    String TeamNameTag = "TEAMNAME:";
    String ShootLocationsTag = "SHOOTLOCATION:";
    char shotBatter = 'b'; char didntShootBatter = '0';
    char shotWorks = 'w'; char didntShootWorks = '0';

    String ReachedTag = "REACHED:";
    String CrossedTag = "CROSSED:";
    String SpyTag = "SPY:";

    String BeginNotesTag = "EXTRANOTES:";

    String ScaledTag = "SCALED:";
    String ChallengedTag = "CHALLENGED:";

    String BeginObstaclesTag = "BEGINOBSTACLES:";
    String BeginTeleopTag = "BEGINTELEOP:";
    String BeginAutonTag = "BEGINAUTON:";
    String AutonHighGoalsTag = "AUTONHIGH:";
    String AutonLowGoalsTag = "AUTONLOW:";
    String TeleopHighGoalsTag = "TELEOPHIGH:";
    String TeleopLowGoalsTag = "TELEOPLOW:";
    String BeginMissesTag = "MISSES:";

    enum Obstacles
    {
        ObstacleLowBar,
        ObstaclePorticullis,
        ObstacleRockWall,
        ObstacleRamparts,
        ObstacleMoat,
        ObstacleChevalDeFris,
        ObstacleDrawbridge,
        ObstacleSallyPort,
        NumberOfObstacles
    }
    char ObstacleTags[] =
            {
                    'l', // Low Bar
                    'p', // Porticullis
                    'w', // Rock wall
                    'r', // Ramparts
                    'm', // Moat
                    'c', // Cheval de Fris
                    'd', // Drawbridge
                    's' // Sallyport
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        InitializeComponents();
    }

    private void InitializeComponents()
    {
        shotFromBatter = (ToggleBox) findViewById(R.id.batterShoot);
        shotFromOuterWorks = (ToggleBox) findViewById(R.id.outerShoot);

        sallyPort = (ToggleBox) findViewById(R.id.sallyPort);
        drawbridge = (ToggleBox) findViewById(R.id.drawbridge);
        porticullis = (ToggleBox) findViewById(R.id.porticullis);
        cheval = (ToggleBox) findViewById(R.id.cheval);
        ramparts = (ToggleBox) findViewById(R.id.ramparts);
        moat = (ToggleBox) findViewById(R.id.moat);
        rockWall = (ToggleBox) findViewById(R.id.rockWall);
        roughTerrain = (ToggleBox) findViewById(R.id.roughTerrain);

        extraNotes = (EditText) findViewById(R.id.notesText);
        matchNumber = (EditText) findViewById(R.id.matchNumberText);

        autonReached = (ToggleBox) findViewById(R.id.autonReached);
        autonCrossed = (ToggleBox) findViewById(R.id.autonCrossed);
        isSpy = (ToggleBox) findViewById(R.id.spy);

        ToggleBox[] obstacle = { // Indexes correspond to enums
               null,
                porticullis,
                rockWall,
                ramparts,
                moat,
                cheval,
                drawbridge,
                sallyPort,
                roughTerrain
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

        teleopHighGoals = (EditText) findViewById(R.id.telopHigh);
        teleopLowGoals = (EditText) findViewById(R.id.telopLow);
        autonHighGoals = (EditText) findViewById(R.id.autonHigh);
        autonLowGoals = (EditText) findViewById(R.id.autonLow);
        misses = (EditText) findViewById(R.id.missesText);

        challenged = (ToggleBox) findViewById(R.id.challenged);
        scaled = (ToggleBox) findViewById(R.id.scaled);

        teamNumber = (EditText) findViewById(R.id.teamNumberControl);



        doneButton = (Button) findViewById(R.id.save);
    }

    // Erases all data and reverts to default values
    public void Reset()
    {
        lowBarAuton.setText("0");
        lowBarTeleop.setText("0");
        porticullisAuton.setText("0");
        porticullisTeleop.setText("0");
        drawbridgeAuton.setText("0");
        drawbridgeTeleop.setText("0");
        sallyPortAuton.setText("0");
        sallyPortTeleop.setText("0");
        chevalAuton.setText("0");
        chevalTeleop.setText("0");
        rampartsAuton.setText("0");
        rampartsTeleop.setText("0");
        moatAuton.setText("0");
        moatTeleop.setText("0");
        rockWallAuton.setText("0");
        rockWallTeleop.setText("0");

        rockWall.setChecked(false);
        rockWall.SetListener();
        porticullis.setChecked(false);
        porticullis.SetListener();
        cheval.setChecked(false);
        cheval.SetListener();
        moat.setChecked(false);
        moat.SetListener();
        sallyPort.setChecked(false);
        sallyPort.SetListener();
        drawbridge.setChecked(false);
        drawbridge.SetListener();
        ramparts.setChecked(false);
        ramparts.SetListener();
        roughTerrain.setChecked(false);
        roughTerrain.SetListener();

        extraNotes.setText("");
        teamNumber.setText("0");
        matchNumber.setText("0");

        autonHighGoals.setText("0");
        teleopHighGoals.setText("0");
        autonLowGoals.setText("0");
        teleopLowGoals.setText("0");
        misses.setText(0);

        shotFromBatter.setChecked(false);
        shotFromBatter.SetListener();
        shotFromOuterWorks.setChecked(false);
        shotFromOuterWorks.SetListener();
        scaled.setChecked(false);
        scaled.SetListener();
        challenged.setChecked(false);
        challenged.SetListener();
        autonReached.setChecked(false);
        autonReached.SetListener();
        isSpy.setChecked(false);
        isSpy.SetListener();
        autonCrossed.setChecked(false);
        autonCrossed.SetListener();
    }

    // Saves the team to a file
    public void SaveTeam(int inputTeamNumber)
    {
        File fDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS);

        // Column Separator
        String cs = ",";

        String output = "";
        String endl = "\n";
        /*
        output += BeginTag + endl
                + TeamNameTag + String.valueOf(inputTeamNumber) + endl
                + MatchNumberTag + matchNumber.getText() + endl
                // Teleop
        + BeginTeleopTag + endl
                + BeginObstaclesTag + endl

                + ObstacleTags[Obstacles.ObstaclePorticullis.ordinal()] + B2S(porticullis.isChecked()) + porticullisTeleop.getText() + endl
                + ObstacleTags[Obstacles.ObstacleRockWall.ordinal()] + B2S(rockWall.isChecked()) + rockWallTeleop.getText() + endl
                + ObstacleTags[Obstacles.ObstacleRamparts.ordinal()] + B2S(ramparts.isChecked()) + rampartsTeleop.getText() + endl
                + ObstacleTags[Obstacles.ObstacleMoat.ordinal()] + B2S(moat.isChecked()) + moatTeleop.getText() + endl
                + ObstacleTags[Obstacles.ObstacleChevalDeFris.ordinal()] + B2S(cheval.isChecked()) + chevalTeleop.getText() + endl
                + ObstacleTags[Obstacles.ObstacleDrawbridge.ordinal()] + B2S(drawbridge.isChecked()) + drawbridgeTeleop.getText() + endl
                + ObstacleTags[Obstacles.ObstacleSallyPort.ordinal()] + B2S(sallyPort.isChecked()) + sallyPortTeleop.getText() + endl

                + EndTag + endl
                + TeleopHighGoalsTag  + teleopHighGoals.getText() + endl
                + TeleopLowGoalsTag + teleopLowGoals.getText() + endl
                // Auton
        + BeginAutonTag + endl
                + ReachedTag + B2S(autonReached.isChecked()) + endl
                + CrossedTag + B2S(autonCrossed.isChecked()) + endl
                + SpyTag + B2S(isSpy.isChecked()) + endl

                + BeginObstaclesTag + endl

                + ObstacleTags[Obstacles.ObstaclePorticullis.ordinal()] + B2S(porticullis.isChecked()) + porticullisAuton.getText() + endl
                + ObstacleTags[Obstacles.ObstacleRockWall.ordinal()] + B2S(rockWall.isChecked()) + rockWallAuton.getText() + endl
                + ObstacleTags[Obstacles.ObstacleRamparts.ordinal()] + B2S(ramparts.isChecked()) + rampartsAuton.getText() + endl
                + ObstacleTags[Obstacles.ObstacleMoat.ordinal()] + B2S(moat.isChecked()) + moatAuton.getText() + endl
                + ObstacleTags[Obstacles.ObstacleChevalDeFris.ordinal()] + B2S(cheval.isChecked()) + chevalAuton.getText() + endl
                + ObstacleTags[Obstacles.ObstacleDrawbridge.ordinal()] + B2S(drawbridge.isChecked()) + drawbridgeAuton.getText() + endl
                + ObstacleTags[Obstacles.ObstacleSallyPort.ordinal()] + B2S(sallyPort.isChecked()) + sallyPortAuton.getText() + endl

                + EndTag + endl
                + AutonHighGoalsTag + autonHighGoals.getText() + endl
                + AutonLowGoalsTag + autonLowGoals.getText() + endl
                // Shoot locations and other checkboxes
                + ShootLocationsTag
                + (shotFromOuterWorks.isChecked() ? shotWorks : didntShootWorks)
                + (shotFromBatter.isChecked() ? shotBatter : didntShootBatter) + endl
                + BeginMissesTag + misses.getText() + endl
                + ChallengedTag + B2S(challenged.isChecked()) + endl
                + ScaledTag + B2S(scaled.isChecked()) + endl
                + BeginNotesTag + endl + extraNotes.getText() + endl + EndTag + EndTag;
        */

        output += teamNumber.getText() + cs + matchNumber.getText();

        Reset();
        FileOutputStream out;
        try {
            File f = new File(fDir.getAbsolutePath() + "/" +  String.valueOf(inputTeamNumber) + ".team");
            MessageDialog("File Path:", f.getAbsolutePath());
            out = new FileOutputStream(f, true);
            out.write(output.getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // When the user wants to save
    public void DoneClick(View view)
    {
        int obstaclesChecked = 0;
        for (ToggleBox t : obstacles) {
            if (t!=null) {
                if (t.isChecked()) {
                    obstaclesChecked++;
                }
            }
        }

        if (obstaclesChecked != 4) {
            MessageDialog("Bad Number", "You have not provided exactly four obstacles");
            return;
        }

        int num = Integer.parseInt(teamNumber.getText().toString());

        if (num<0)
        {
            MessageDialog("Bad Number", "Invalid Team Number");
            return;
        }

        /*if (!MessageDialog("Confirm", "Would you like to save, and reset progress?"))
        {
            return;
        }*/
        SaveTeam(num);
    }

    // Obtain a tag from the line
    public String ObtainTag(String line)
    {
        int i = 0;
        if (line.charAt(i)==EndTag.charAt(0))
        {
            return EndTag;
        }

        ++i;
        String Tag  = "";
        while (line.charAt(i)!=EndTag.charAt(0))
        {
            Tag += line.charAt(i);
        }

        return Tag + EndTag;
    }

    // Converts a bool to a string equivalent
    public String B2S(boolean bool)
    {
        if (bool)
            return "1";
        return "0";
    }

    public boolean positiveDialog = false;
    public boolean MessageDialog(String title, String text)
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(text);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        positiveDialog = false;
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HOT67FRCScouter.this.positiveDialog = true;
                        dialog.dismiss();
                    }
                });
        dlgAlert.create().show();
        return positiveDialog;
    }
}