package com.hv.dynamicgridhv.example;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hivedi.dynamicgrid.DynamicGridViewHv;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DynamicGridViewHv grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = (DynamicGridViewHv) findViewById(R.id.grid);
        grid.setAdapter(new GridAdapter(this, 2));
    }

    public class GridAdapter extends BaseDynamicGridAdapter {

        protected GridAdapter(Context context, int columnCount) {
            super(context, columnCount);
            ArrayList<String> it = new ArrayList<>();
            for(int i=0; i<14; i++) {
                it.add("Item " + i);
            }
            set(it);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView res = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
            res.setText(getItem(position).toString());
            return res;
        }
    }
}
