package com.stackbuffers.myguardianangels.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stackbuffers.myguardianangels.Activities.Edit_Angel_Activity;
import com.stackbuffers.myguardianangels.Adapter.GuardianAngelAdapter;
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


public class Guardian_Angel_Fragment extends Fragment implements DeleteListener, UpdateListener {

    ImageView add_btn;
    SharedPreferences preferences;
    String id;
    List<AngelData> list = new ArrayList<>();
    List<AngelData> allDataList = new ArrayList<>();
    RecyclerView recyclerview;
    int recordId;
    ProgressBar progress_bar;
    TextView noDataFound;
    EditText search_ed;
    GuardianAngelAdapter guardianAngelAdapter;


    public Guardian_Angel_Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_guardian__angel_, container, false);
        preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        id = preferences.getString("userId", " ");
        add_btn = view.findViewById(R.id.add_btn);
        recyclerview = view.findViewById(R.id.recyclerview);
        progress_bar = view.findViewById(R.id.progress_bar);
        noDataFound = view.findViewById(R.id.noDataFound);
        search_ed = view.findViewById(R.id.search_ed);

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setHasFixedSize(true);
        guardianAngelAdapter = new GuardianAngelAdapter(list, getActivity(), this::deleteRequest, this::acceptRequest);
        recyclerview.setAdapter(guardianAngelAdapter);

        loadData(id);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Add_Angel_Fragment editProfile_fragment = new Add_Angel_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame1, editProfile_fragment);
                fragmentTransaction.commit();

            }
        });

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
        guardianAngelAdapter.notifyDataSetChanged();
    }

    private void loadData(String id) {
        progress_bar.setVisibility(View.VISIBLE);
        noDataFound.setVisibility(View.GONE);
        Call<Angel_Request_Response> call = RetrofitClientInstance.getInstance().getApi().requestToAngel(id);
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

                    guardianAngelAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), res != null ? res.getMessage() : "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                if (list.isEmpty())
                    noDataFound.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Angel_Request_Response> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void deleteRequest(int position) {
        recordId = list.get(position).getId();
        Call<Angel_Delete_Request> call = RetrofitClientInstance.getInstance().getApi().deleteGuardianAngel(recordId);
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

    @Override
    public void acceptRequest(int position) {


        Intent i = new Intent(getActivity(), Edit_Angel_Activity.class);
        i.putExtra("user_id", list.get(position).getUser_id());
        i.putExtra("username", list.get(position).getName());
        i.putExtra("userEmail", list.get(position).getEmail());
        i.putExtra("relation", list.get(position).getAngel_relation());
        i.putExtra("angel_id", list.get(position).getAngel_id());
        i.putExtra("record_id", String.valueOf(list.get(position).getId()));
        startActivity(i);

    }
}