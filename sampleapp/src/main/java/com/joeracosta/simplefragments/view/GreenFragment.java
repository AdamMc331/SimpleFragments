package com.joeracosta.simplefragments.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.joeracosta.library.activity.FragmentStackFragment;
import com.joeracosta.library.activity.SimpleFragment;
import com.joeracosta.simplefragments.R;

/**
 * Created by Joe on 8/14/2017.
 */

public class GreenFragment extends SimpleFragment {

    public static GreenFragment newInstance(int stackLevel){
        Bundle args = new Bundle();
        args.putInt(SampleMapActivity.STACK_LEVEL_KEY, stackLevel);
        GreenFragment fragment = new GreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int stackLevel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null){
            stackLevel = args.getInt(SampleMapActivity.STACK_LEVEL_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.green_fragment, container, false);
        ((TextView)view.findViewById(R.id.stack_num)).setText(Integer.toString(stackLevel));

        ((Button)view.findViewById(R.id.deeper_again)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentStackFragment) getParentFragment()).addFragmentToStack(GreenFragment.newInstance(stackLevel+1), R.id.full_container, null, null);
            }
        });

        return view;
    }

    @Override
    public void onShown() {
        System.out.print("");
    }

    @Override
    public void onHidden() {
        System.out.print("");
    }
}