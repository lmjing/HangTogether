package com.example.woojinroom.hangto.TabActivity.Tab5MyPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.woojinroom.hangto.R;
import com.example.woojinroom.hangto.TabActivity.ParentFragment.TabParentFragment;
import com.example.woojinroom.hangto.TabActivity.TabActivity;
import com.example.woojinroom.hangto.editActivity;
import com.example.woojinroom.hangto.historyActivity;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class Tab5MyPageFragment extends TabParentFragment {
    TabActivity activity;
    Button button5;
    Button button6;

    public Tab5MyPageAdapter adapter;
    //private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    public int page = 1;
    public boolean endOfPage = false;
    //SwipeRefreshLayout pullToRefresh;

    /**
     * Create a new instance of the fragment
     */
    public static Tab5MyPageFragment newInstance(int index) {
        Tab5MyPageFragment fragment = new Tab5MyPageFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final TabActivity tabActivity = (TabActivity) getActivity();
        this.activity = tabActivity;

       // Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);
        //activity.setSupportActionBar(cs_toolbar);
       // activity.getSupportActionBar().setTitle("내정보");

        /*if (recyclerView == null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
        }*/

        if (adapter == null) {
            adapter = new Tab5MyPageAdapter(new Tab5MyPageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            }, activity, this);
        }
        //recyclerView.setAdapter(adapter);

       // indicator = (LinearLayout)view.findViewById(R.id.indicator);
        /*pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pull_to_refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh.setRefreshing(false);
                refresh();
            }
        });*/
        button5=(Button)view.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent edit_intent = new Intent(view.getContext(), editActivity.class);
                startActivity(edit_intent);
            }
        });
        button6=(Button)view.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent history_intent = new Intent(view.getContext(), historyActivity.class);
                startActivity(history_intent);
            }
        });

        connectTestCall();
    }

    @Override
    public void refresh() {
        page = 1;
        endOfPage = false;
        adapter.clear();
        adapter.notifyDataSetChanged();
        connectTestCall();
    }

    @Override
    public void reload() {

    }

    void connectTestCall() {

    }
}
