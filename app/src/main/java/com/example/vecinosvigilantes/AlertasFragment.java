package com.example.vecinosvigilantes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AlertasFragment extends Fragment {
    public AlertasFragment() {
        // Required empty public constructor
    }

    public static AlertasFragment newInstance(String param1, String param2) {
        AlertasFragment fragment = new AlertasFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_alertas2, container, false);
    }
}