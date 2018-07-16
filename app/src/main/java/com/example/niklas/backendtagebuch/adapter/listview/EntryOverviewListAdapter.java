package com.example.niklas.backendtagebuch.adapter.listview;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.niklas.backendtagebuch.R;
import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;

import java.util.Calendar;
import java.util.List;

public class EntryOverviewListAdapter extends ArrayAdapter<Entry>  {

    public EntryOverviewListAdapter(final Context context, final List<Entry> objects){
        super(context,0,objects);

    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        Entry currentEntry = getItem(position);

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.entry_overview_listitem, parent, false);
        }

        ((TextView) view.findViewById(R.id.titletv)).setText(currentEntry.getTitle());

        ((TextView) view.findViewById(R.id.datetv)).setText(currentEntry.getDate());

        return view;

    }

}