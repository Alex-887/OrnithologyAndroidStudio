package com.example.ornithology_favre_berthouzoz.ui.main;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ornithology_favre_berthouzoz.R;

public class DescriptionFragment extends Fragment {

    private TextView descriptionDisplay;

    public static final String ARG_DESCRIPTION ="argDescription";
    public static final String ARG_BIOLOGY= "argBiology";

    private String description;
    private String biology;



    public DescriptionFragment() {
        // Required empty public constructor
    }



    public static DescriptionFragment newInstance(String description, String biology) {
        DescriptionFragment fragment = new DescriptionFragment();

        Bundle args = new Bundle();
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_BIOLOGY, biology);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_description, container, false);
        descriptionDisplay = v.findViewById(R.id.txt_description);

        String doubleSpace = "\n" + "\n";
        //if there is nothing, it makes the app crashes
        if (getArguments() != null) {
            description = getArguments().getString(ARG_DESCRIPTION);
            biology = getArguments().getString(ARG_BIOLOGY);
        }

            descriptionDisplay.setText("Description : " + description + doubleSpace + "Bio : " + biology);

         return v;

    }




}
