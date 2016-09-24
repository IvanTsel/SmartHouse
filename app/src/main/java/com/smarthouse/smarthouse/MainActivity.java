package com.smarthouse.smarthouse;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    private TabHost _tabHost;
    private int _tabCount;
    private String _tag;
    private MenuItem _menuAddDeviceItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _tag = "TAG";
        _tabCount = 0;
        _tabHost = (TabHost) findViewById(R.id.tabHost);
        _tabHost.setup();
    }

    private OnLongClickListener _onLongClick = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(getApplicationContext(),"Вы хотите удалить вкладку",Toast.LENGTH_LONG).show();
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toast_menu, menu);
        _menuAddDeviceItem = menu.findItem(R.id.action_add_device);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add_room:
                final View dialogView = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
                final EditText roomName = (EditText) dialogView.findViewById(R.id.room_name);
                AlertDialog.Builder builderDialog = new AlertDialog.Builder(this);
                builderDialog.setView(dialogView);
                builderDialog.setTitle("Название комнаты");
                builderDialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String tag = _tag + Integer.toString(_tabCount);
                        TabHost.TabSpec tabSpec = _tabHost.newTabSpec(tag);
                        tabSpec.setContent(tabContentFactory);
                        tabSpec.setIndicator(roomName.getText());
                        _tabHost.addTab(tabSpec);
                        _tabHost.getTabWidget().getChildAt(_tabCount).setOnLongClickListener(_onLongClick);
                        _tabHost.setCurrentTab(_tabCount++);
                        if(!_menuAddDeviceItem.isEnabled() && _tabHost.getChildCount() > 0)
                        {
                            _menuAddDeviceItem.setEnabled(true);
                        }
                    }
                });
                builderDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog addDialog = builderDialog.create();
                addDialog.show();
                break;
            case R.id.action_setting:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    TabHost.TabContentFactory tabContentFactory = new TabHost.TabContentFactory()
    {
        @Override
        public View createTabContent(String tag)
        {
            TextView textView = new TextView(MainActivity.this);
            textView.setText(tag);
            return textView;
        }
    };

}
