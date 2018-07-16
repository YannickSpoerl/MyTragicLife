package com.example.niklas.backendtagebuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.niklas.backendtagebuch.adapter.listview.EntryOverviewListAdapter;
import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        private ListView listView;
        private List<Entry> data;
        private EntryOverviewListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("My Diary: Main Page");
        TextView noEntries = (TextView) findViewById(R.id.tvNoEntries);
        this.listView = (ListView) findViewById(R.id.entries);
        this.data = EntryDatabase.getInstance(this).readAllEntries();
/*      Button btndeleteall = (Button) findViewById(R.id.btndeleteall);
        Button btndeletefirst = (Button) findViewById(R.id.btndeletefirst);
        Button newEntrybtn = (Button) findViewById(R.id.createEntry);           */
        this.adapter = new EntryOverviewListAdapter(this, data);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object element = adapterView.getAdapter().getItem(i);

                if(element instanceof Entry){
                    Entry entry = (Entry) element;
                    Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                    intent.putExtra(ContentActivity.ENTRY_ID_KEY,entry.getId());
                    startActivity(intent);
                }



            }
        });
        refreshListView();
        if (EntryDatabase.getInstance(MainActivity.this).getFirstEntry() == null) {
            noEntries.setVisibility(View.VISIBLE);
        }else {
            noEntries.setVisibility(View.INVISIBLE);
        }
/*        newEntrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateNewEntry.class);
                startActivity(intent);
            }
        });

        if(btndeleteall != null){
            btndeleteall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EntryDatabase database = EntryDatabase.getInstance(MainActivity.this);
                    database.deleteallEntries();
                    refreshListView();
                }
            });
        }
        if(btndeletefirst != null){
            btndeletefirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EntryDatabase database = EntryDatabase.getInstance(MainActivity.this);
                    Entry first = database.getFirstEntry();
                    if(first != null){
                        database.deleteEntry(first);
                        refreshListView();
                    }
                }
            });
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar1,menu);

        // Inflate and initialize the bottom menu
        ActionMenuView bottomBar = (ActionMenuView) findViewById(R.id.bottomToolbar);
        Menu bottomMenu = bottomBar.getMenu();
        getMenuInflater().inflate(R.menu.toolbar1, bottomMenu);
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
        TextView noEntries = (TextView) findViewById(R.id.tvNoEntries);
        switch (item.getItemId()) {
            case R.id.tlbPlus:
                Intent intent = new Intent(MainActivity.this,CreateNewEntry.class);
                startActivity(intent);
                return true;
            case R.id.miDeleteAll:
                EntryDatabase database0 = EntryDatabase.getInstance(MainActivity.this);
                database0.deleteallEntries();
                refreshListView();
                noEntries.setVisibility(View.VISIBLE);
                Toast toast= Toast.makeText(getApplicationContext(), "All Deleted.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 200);
                toast.show();
                return true;
            case R.id.miDeleteFirst:
                EntryDatabase database = EntryDatabase.getInstance(MainActivity.this);
                Entry first = database.getFirstEntry();
                if(first != null){
                    database.deleteEntry(first);
                    refreshListView();
                    if(database.getFirstEntry() == null){
                        noEntries.setVisibility(View.VISIBLE);
                    }
                    Toast toast2= Toast.makeText(getApplicationContext(), "First Deleted.", Toast.LENGTH_SHORT);
                    toast2.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 200);
                    toast2.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void refreshListView(){
        data.clear();
        data.addAll(EntryDatabase.getInstance(this).readAllEntries());
        adapter.notifyDataSetChanged();
    }
}
