package com.ghf.sidebardemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ghf.sidebardemo.Contact;
import com.ghf.sidebardemo.ContactAdapter;
import com.ghf.sidebardemo.R;
import com.ghf.sidebardemo.widgets.WaveSideBar;

import java.util.ArrayList;
import java.util.List;

public class RightPositionActivity extends AppCompatActivity {


    /*
    * https://github.com/gjiazhe/WaveSideBar
    * */

    private RecyclerView recyclerView;

    private List<Contact> contacts = new ArrayList<>();
    private WaveSideBar waveSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_position);

        initData();
        initView();

    }

    private void initView() {

        recyclerView = (RecyclerView) findViewById(R.id.rv_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ContactAdapter(contacts));

        waveSideBar = (WaveSideBar) findViewById(R.id.side_bar);
//        waveSideBar.setPosition(WaveSideBar.POSITION_LEFT);//设置SideBar在左边
        waveSideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void initData() {
        contacts.addAll(Contact.getEnglishContacts());

    }
}
