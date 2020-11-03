package com.example.exp03_04;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public String[] names = new String[] {"One", "Two", "Three", "Four", "Five", "Six"};
    public int[] images = new int[] {R.drawable.my_android};
    public int selected_items = 0;
    public ActionMode am;
    public HashMap<View, Boolean> vis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vis = new HashMap<>();

        List<Map<String, Object>> items = new ArrayList<>();
        for(int i=0; i < names.length; i++){
            Map<String, Object> item = new HashMap<>();
            item.put("image_android", images[0]);
            item.put("text_item", names[i]);
            items.add(item);
        }

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, items, R.layout.simadapter,
                new String[]{"image_android", "text_item"}, new int[]{R.id.image_my_android, R.id.text_item});
        ListView listView = findViewById(R.id.my_list);

        listView.setAdapter(simpleAdapter);

    }
    //创建ActionMode
    ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_context, menu);
            return true;
        }
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            mode.setTitle(selected_items+"selected");
            return true;
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };

    public void click_select(View V){
        if(am == null){
            am = startActionMode(callback);
        }
        LinearLayout linearLayout = (LinearLayout) V;
        if( vis.get(V) == null || !vis.get(V)){
            linearLayout.setBackgroundColor(Color.GRAY);
            vis.put(V, true);
            selected_items++;
        }else{
            linearLayout.setBackgroundColor(Color.WHITE);
            vis.put(V, false);
            selected_items--;
        }
        callback.onActionItemClicked(am, null);
    }
}