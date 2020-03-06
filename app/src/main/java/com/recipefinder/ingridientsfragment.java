package com.recipefinder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipefinder.utils.Ingridient;
import com.recipefinder.utils.IngridientAdapter;
import java.util.ArrayList;

public class ingridientsfragment extends Fragment {
    private RecyclerView mIngridientList;
    private IngridientAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingridients_fragment, container, false);
        mIngridientList = view.findViewById(R.id.ingridient_list);

        String[] name = this.getArguments().getStringArray("names");
        String[] imgurl = this.getArguments().getStringArray("img");
        String[] amount = this.getArguments().getStringArray("amount");
        String[] unit = this.getArguments().getStringArray("unit");

        ArrayList<Ingridient> list = new ArrayList<>();

        for(int i = 0; i < name.length; i++){
            list.add(new Ingridient(name[i], imgurl[i], amount[i], unit[i]));
        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mIngridientList.setLayoutManager(layoutManager);
        mIngridientList.setHasFixedSize(true);
        adapter = new IngridientAdapter();
        adapter.setIngridientData(list);

        mIngridientList.setAdapter(adapter);

        return view;
    }
}
