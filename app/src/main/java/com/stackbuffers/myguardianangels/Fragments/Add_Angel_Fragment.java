package com.stackbuffers.myguardianangels.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ameer.awesome.utility.Utility;
import com.stackbuffers.myguardianangels.Models.Angel_Request_Response;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Add_Angel_Fragment extends Fragment {

    EditText full_name, email_et, relationship;
    Button add_btn;
    SharedPreferences preferences;
    String id;

    ProgressBar progressBar;


    public Add_Angel_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add__angel_, container, false);
        full_name = view.findViewById(R.id.full_name);
        email_et = view.findViewById(R.id.email_et);
        relationship = view.findViewById(R.id.relationship);
        add_btn = view.findViewById(R.id.add_btn);
        progressBar = view.findViewById(R.id.progress_bar);
        SharedPreferences preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
//        SharedPreferences pref = getActivity().getSharedPreferences("Gcredentials",Context.MODE_PRIVATE);


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = full_name.getText().toString().trim();
                String email = email_et.getText().toString().trim();
                String relation = relationship.getText().toString();
                id = preferences.getString("userId", " ");
                addAngel(id, name, email, relation);
            }
        });
        return view;
    }

    private void addAngel(String id, String name, String email, String relation) {
        Utility.Companion.hideKeyboard(requireActivity());
        progressBar.setVisibility(View.VISIBLE);
        Call<Angel_Request_Response> call = RetrofitClientInstance.getInstance().getApi().addAngel(id, name, email, relation);
        call.enqueue(new Callback<Angel_Request_Response>() {
            @Override
            public void onResponse(Call<Angel_Request_Response> call, Response<Angel_Request_Response> response) {
                progressBar.setVisibility(View.GONE);
                Angel_Request_Response res = response.body();
                if (response.isSuccessful() && res != null && res.getStatus() == 200) {
                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), res != null ? res.getMessage() : "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Angel_Request_Response> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}