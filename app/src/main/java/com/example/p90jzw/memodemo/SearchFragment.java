package com.example.p90jzw.memodemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    @BindView(R.id.search_recyclerView)
    RecyclerView serchRecyclerView;

    private SearchAdapter searchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        searchAdapter = new SearchAdapter();
        serchRecyclerView.setAdapter(searchAdapter);
        serchRecyclerView.setHasFixedSize(true);
        return view;
    }
}