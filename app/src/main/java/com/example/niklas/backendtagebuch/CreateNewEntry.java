package com.example.niklas.backendtagebuch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Date;

public class CreateNewEntry extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    public Button locateme;
    //public Button back;
    public GoogleMap map;
    public Marker marker;
    public LocationManager locationManager;
    public EditText title;
    public EditText content;
   //public Button save;
    public Entry entry;
    public EditText date_day;
    public EditText date_month;
    public EditText date_year;
    public EditText latitude;
    public EditText longitude;
    public String day;
    public String month;
    public String year;
    public Button getdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_entry);

        getSupportActionBar().setTitle("My Diary: New Entry");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.entry = new Entry();
        this.locateme = (Button) findViewById(R.id.locate);
//        this.save = (Button) findViewById(R.id.save);
        this.title = (EditText) findViewById(R.id.title);
        this.content = (EditText) findViewById(R.id.content);
//        this.back = (Button) findViewById(R.id.back);
        this.date_day = (EditText) findViewById(R.id.date_day);
        this.date_month = (EditText) findViewById(R.id.date_month);
        this.date_year = (EditText) findViewById(R.id.date_year);
        //this.latitude = (EditText) findViewById(R.id.latitude);
        //this.longitude = (EditText)findViewById(R.id.Longitude);
        this.getdate = (Button) findViewById(R.id.getdate);

        Intent intent = getIntent();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment2);
        mapFragment.getMapAsync(this);


        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (this.locateme != null) {
            this.locateme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    searchPosition();
                }
            });
        }

        this.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                entry.setTitle(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                entry.setContent(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

            this.getdate.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(final View view){
                    Date actualDate = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(actualDate);
                    date_day.setText(Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
                    date_month.setText(Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1));
                    date_year.setText(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                }
        });

        /*this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(entry.getLocation()==null){
                    double lat = new Double(latitude.getText().toString());
                    double longi = new Double(longitude.getText().toString());
                    LatLng position = new LatLng(lat,longi);
                    entry.setLocation(position);
                }
                entry.setDate(date_day.getText()+"."+date_month.getText()+"."+date_year.getText());
                if((entry.getLocation() == null) || (entry.getDate() == null) || (entry.getTitle() == null) || (entry.getContent() == null)){
                    Toast.makeText(CreateNewEntry.this, "Fehler beim Speichern, bitte alle Angababen befüllen.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                EntryDatabase.getInstance(CreateNewEntry.this).createEntry(entry);
                    Intent intent = new Intent(CreateNewEntry.this, MainActivity.class);
                    startActivity(intent);
            }}
        });

        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewEntry.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tlbSave:
                /*if(entry.getLocation()==null){
                    if(latitude.getText().toString()!="" || longitude.getText().toString()!=""){
                        double lat = new Double(latitude.getText().toString());
                        double longi = new Double(longitude.getText().toString());
                        LatLng position = new LatLng(lat,longi);
                        entry.setLocation(position);
                    }
                }*/
                if(entry.getLocation()==null)
                    entry.setLocation(new LatLng(0,0));
                entry.setDate(date_day.getText()+"."+date_month.getText()+"."+date_year.getText());
                if((entry.getLocation() == null) || (entry.getTitle() == null) || (entry.getContent() == null) || !validateDate()){
                    Toast toast= Toast.makeText(getApplicationContext(), "Could not save, please enter all data correctly.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 200);
                    toast.show();
                    return true;
                }
                else {
                    EntryDatabase.getInstance(CreateNewEntry.this).createEntry(entry);
                    Intent intent = new Intent(CreateNewEntry.this, MainActivity.class);
                    startActivity(intent);
                    Toast toast2= Toast.makeText(getApplicationContext(), "Saved.", Toast.LENGTH_SHORT);
                    toast2.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 200);
                    toast2.show();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreateNewEntry.this, MainActivity.class);
        startActivity(intent);
    }


    public boolean validateDate(){
        if(entry.getDate().equals("..")){
            return false;
        }
        else if(Integer.parseInt(date_day.getText().toString()) == 0 || Integer.parseInt(date_day.getText().toString()) > 31 || Integer.parseInt(date_month.getText().toString()) > 12 || Integer.parseInt(date_month.getText().toString()) == 0 || Integer.parseInt(date_year.getText().toString()) > 2018){
            // still possible to enter dates such as 31.2.2018 but who really cares :)
            return false;
        }
        return true;
    }

    public void searchPosition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermission(5);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 50, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
        entry.setLocation(position);
        if(this.map != null) {

            if (this.marker != null) {
                this.marker.remove();
            }
            this.marker = map.addMarker(new MarkerOptions().position(position));
            this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        }
        removeListener();
    }

    private void removeListener(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            this.requestPermission(4);
        }
        this.locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void requestPermission(final int resultCode){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 5:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    searchPosition();
                }
                break;
            case 4:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    removeListener();
                }
                break;
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
