package com.example.niklas.backendtagebuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContentActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String ENTRY_ID_KEY = "ID";
    TextView title;
    TextView date;
    TextView content;
    private Entry entry;
   // Button share;
   // Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        getSupportActionBar().setTitle("My Diary: List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        long id = getIntent().getLongExtra(ENTRY_ID_KEY,0);
        title=(TextView) findViewById(R.id.title);
        date=(TextView) findViewById(R.id.date_day);
        content=(TextView) findViewById(R.id.content);
    //    share=(Button) findViewById(R.id.share);
    //    delete = (Button) findViewById(R.id.delete);
        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
        fragment.getMapAsync(this);
        this.entry = EntryDatabase.getInstance(this).readEntry(id);
        title.setText(entry.getTitle());
        date.setText(entry.getDate());
        content.setText(entry.getContent());
    /*    share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String text = entry.getTitle() + "\nverfasst am " + entry.getDate()+ ":\n" + entry.getContent();
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, "Teile deinen Tag mit:"));
            }
        });*/

      /*  delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EntryDatabase database = EntryDatabase.getInstance(ContentActivity.this);
                database.deleteEntry(entry);
                Intent intent = new Intent(ContentActivity.this, MainActivity.class);
                startActivity(intent);;

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar3,menu);

        // Inflate and initialize the bottom menu
        ActionMenuView bottomBar = (ActionMenuView) findViewById(R.id.bottomToolbar);
        Menu bottomMenu = bottomBar.getMenu();
        getMenuInflater().inflate(R.menu.toolbar3, bottomMenu);
        for (int i = 0; i < bottomMenu.size(); i++) {
            bottomMenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tlbShare:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String text = entry.getTitle() + "\nwritten on " + entry.getDate()+ ":\n" + entry.getContent();
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, "Share your day with:"));
                return true;
            case R.id.miDelete:
                EntryDatabase database = EntryDatabase.getInstance(ContentActivity.this);
                database.deleteEntry(entry);
                Intent intent2 = new Intent(ContentActivity.this, MainActivity.class);
                startActivity(intent2);
                Toast toast= Toast.makeText(getApplicationContext(), "Deleted.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 200);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(!(entry.getLocation().equals(new LatLng(0,0)))){
            googleMap.addMarker(new MarkerOptions().position(entry.getLocation()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(entry.getLocation(),15));
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
