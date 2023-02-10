package com.stackbuffers.myguardianangels.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stackbuffers.myguardianangels.Adapter.AngelRequest_Adapter;
import com.stackbuffers.myguardianangels.Models.Accept_Request_Response;
import com.stackbuffers.myguardianangels.Models.AngelData;
import com.stackbuffers.myguardianangels.Models.Angel_Delete_Request;
import com.stackbuffers.myguardianangels.Models.Angel_Request_Response;
import com.stackbuffers.myguardianangels.Models.DeleteListener;
import com.stackbuffers.myguardianangels.Models.UpdateListener;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Check_Request_Fragment extends Fragment implements UpdateListener, DeleteListener {
    Dialog dialog;
    RecyclerView recyclerview;

    SharedPreferences preferences;
    String id;
    List<AngelData> list = new ArrayList<>();
    List<AngelData> allDataList = new ArrayList<>();

    int record_id;
    ProgressBar progress_bar;
    TextView noDataFound;
    EditText search_ed;
    AngelRequest_Adapter angelRequest_adapter;


    public Check_Request_Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_check__request_, container, false);
        recyclerview = view.findViewById(R.id.recyclerview);
        progress_bar = view.findViewById(R.id.progress_bar);
        noDataFound = view.findViewById(R.id.noDataFound);
        search_ed = view.findViewById(R.id.search_ed);

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setHasFixedSize(true);
        angelRequest_adapter = new AngelRequest_Adapter(list, getActivity(), this::acceptRequest, this::deleteRequest);
        recyclerview.setAdapter(angelRequest_adapter);


        preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        id = preferences.getString("userId", " ");
        checkRequestData(id);

        dialog = new Dialog(getActivity());
        searchListener();

        return view;
    }

    private void searchListener() {
        search_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                Log.d("TAG", "afterTextChanged: " + keyword);
                searchData(keyword);
            }
        });
    }

    private void searchData(String keyword) {
        list.clear();

        if (keyword.isEmpty()) {
            list.addAll(allDataList);
        } else {
            for (AngelData angelData : allDataList) {
                if (angelData.name != null && angelData.name.toLowerCase(Locale.ROOT).startsWith(keyword.toLowerCase(Locale.ROOT))) {
                    list.add(angelData);
                }
            }
        }
        angelRequest_adapter.notifyDataSetChanged();
    }

    private void checkRequestData(String id) {
        progress_bar.setVisibility(View.VISIBLE);
        noDataFound.setVisibility(View.GONE);
        Call<Angel_Request_Response> call = RetrofitClientInstance.getInstance().getApi().showRequestToAngel(id);
        call.enqueue(new Callback<Angel_Request_Response>() {
            @Override
            public void onResponse(Call<Angel_Request_Response> call, Response<Angel_Request_Response> response) {
                progress_bar.setVisibility(View.GONE);
                Angel_Request_Response res = response.body();
                if (response.isSuccessful() && res != null && res.getStatus() == 200) {
                    list.clear();
                    allDataList.clear();
                    list.addAll(res.getData());
                    allDataList.addAll(res.getData());
                    angelRequest_adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), res != null ? res.getMessage() : "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                if (list.isEmpty()) {
                    noDataFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Angel_Request_Response> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void showPopup() {

        dialog.setContentView(R.layout.check_request_response_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void acceptRequest(int position) {
        record_id = list.get(position).getId();
        Call<Accept_Request_Response> call = RetrofitClientInstance.getInstance().getApi().acceptRequest(record_id);
        call.enqueue(new Callback<Accept_Request_Response>() {
            @Override
            public void onResponse(Call<Accept_Request_Response> call, Response<Accept_Request_Response> response) {
                Accept_Request_Response res = response.body();
                if (response.isSuccessful() && res.getStatus() == 200) {
                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Accept_Request_Response> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void deleteRequest(int position) {

        record_id = list.get(position).getId();
        Call<Angel_Delete_Request> call = RetrofitClientInstance.getInstance().getApi().deleteRequest(String.valueOf(record_id));
        call.enqueue(new Callback<Angel_Delete_Request>() {
            @Override
            public void onResponse(Call<Angel_Delete_Request> call, Response<Angel_Delete_Request> response) {
                Angel_Delete_Request res = response.body();
                if (response.isSuccessful() && res.getStatus() == 200) {
                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Angel_Delete_Request> call, Throwable t) {

            }
        });

    }
}