package com.ghf.sidebardemo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghf.sidebardemo.R;
import com.ghf.sidebardemo.utils.CharacterParser;
import com.ghf.sidebardemo.utils.ConstactUtil;
import com.ghf.sidebardemo.utils.PinyinComparator;
import com.ghf.sidebardemo.utils.SortModel;
import com.ghf.sidebardemo.widgets.ClearEditText;
import com.ghf.sidebardemo.widgets.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContactsActivity extends AppCompatActivity {

    private SideBar sideBar;
    private TextView dialog;
    private ListView sortListView;
    private CharacterParser characterParser;//实例化汉字转拼音类
    private PinyinComparator pinyinComparator;//首字母比较器
    private Map<String, String> callRecords;//所有数据
    private List<SortModel> SourceDateList;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initView();
        initData();
    }


    private void initView() {
        sideBar = (SideBar) this.findViewById(R.id.sidrbar);
        dialog = (TextView) this.findViewById(R.id.dialog);
        sortListView = (ListView) this.findViewById(R.id.sortlist);

    }


    private void initData() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        //首字母比较器
        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = callRecords.get(adapter.getItem(position).getName());
                Toast.makeText(ContactsActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        });

        new ConstactAsyncTask().execute(0);
    }

    class ConstactAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {

            int result = -1;
            callRecords = ConstactUtil.getAllCallRecords(ContactsActivity.this);
            result = 1;
            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == 1) {
                List<String> constact = new ArrayList<>();
                for (Iterator<String> keys = callRecords.keySet().iterator(); keys.hasNext(); ) {
                    String key = keys.next();
                    constact.add(key);
                }

                String[] names = new String[]{};
                names = constact.toArray(names);
                SourceDateList = filledData(names);

                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                adapter = new SortAdapter(ContactsActivity.this, SourceDateList);
                sortListView.setAdapter(adapter);


                mClearEditText = (ClearEditText) ContactsActivity.this
                        .findViewById(R.id.filter_edit);
                mClearEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        mClearEditText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    }
                });

                // 根据输入框输入值的改变来过滤搜索
                mClearEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                        filterData(s.toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }
    }


    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
}
