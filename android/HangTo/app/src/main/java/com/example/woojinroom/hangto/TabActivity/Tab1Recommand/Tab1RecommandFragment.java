package com.example.woojinroom.hangto.TabActivity.Tab1Recommand;

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
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.woojinroom.hangto.Model.Food;
import com.example.woojinroom.hangto.R;
import com.example.woojinroom.hangto.TabActivity.ParentFragment.TabParentFragment;
import com.example.woojinroom.hangto.TabActivity.TabActivity;
import com.example.woojinroom.hangto.TabActivity.TabActivity_;
import com.example.woojinroom.hangto.searchActivity;
import com.example.woojinroom.hangto.writeActivity;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class Tab1RecommandFragment extends TabParentFragment {
    TabActivity activity;
    ImageButton button;
    ImageButton imageButton_search;

    public Tab1RecommandAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    public int page = 1;
    public boolean endOfPage = false;
    SwipeRefreshLayout pullToRefresh;

    /**
     * Create a new instance of the fragment
     */
    public static Tab1RecommandFragment newInstance(int index) {
        Tab1RecommandFragment fragment = new Tab1RecommandFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommand, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final TabActivity tabActivity = (TabActivity) getActivity();
        this.activity = tabActivity;

        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("LOGO");

        if (recyclerView == null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
        }

        if (adapter == null) {
            adapter = new Tab1RecommandAdapter(new Tab1RecommandAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            }, activity, this);
        }
        recyclerView.setAdapter(adapter);

       // indicator = (LinearLayout)view.findViewById(R.id.indicator);
        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pull_to_refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh.setRefreshing(false);
                refresh();
            }
        });
        button=(ImageButton)view.findViewById(R.id.button_write);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent posting_intent = new Intent(view.getContext(), writeActivity.class);
                startActivity(posting_intent);
            }
        });

        imageButton_search=(ImageButton)view.findViewById(R.id.imagebutton_search);
        imageButton_search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent search_intent = new Intent(view.getContext(),searchActivity.class);
                startActivity(search_intent);
            }
        });
        connectRecommand();
    }

    @Override
    public void refresh() {
        page = 1;
        endOfPage = false;
        adapter.clear();
        adapter.notifyDataSetChanged();
        connectRecommand();
    }

    @Override
    public void reload() {

    }

    void connectRecommand() {
        for (int i=0; i<10; i++)
            adapter.addData(Food.mockFood(i));
        adapter.notifyDataSetChanged();
    }
    public void profile(Intent intent){
        startActivity(intent);
    }
}
