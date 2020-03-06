package com.recipefinder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class stepsfragment extends Fragment {
    private TextView mText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_fragment, container, false);
        mText = view.findViewById(R.id.steps);
        String[] steps = this.getArguments().getStringArray("instructions");

        for(int i = 0; i < steps.length; i++){
            String step = i + 1 + ") " + steps[i] + "\n\n";
            mText.append(step);
        }

        return view;
    }
}
