package com.stackbuffers.myguardianangels.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ameer.awesome.utility.Utility;
import com.google.gson.JsonObject;
import com.stackbuffers.myguardianangels.Adapter.MyEvedenceAdapter;
import com.stackbuffers.myguardianangels.Models.myEvidence.MyEvidence;
import com.stackbuffers.myguardianangels.Models.myEvidence.MyEvidenceResponse;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;
import com.stackbuffers.myguardianangels.databinding.FragmentMyEvidenceFragmentBinding;
import com.stackbuffers.myguardianangels.interfaces.EvdClickListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Angels_Evidence_Fragment extends Fragment {

    FragmentMyEvidenceFragmentBinding binding;
    MyEvedenceAdapter myEvedenceAdapter;
    ArrayList<MyEvidence> myEvidenceArrayList = new ArrayList<>();
    ArrayList<MyEvidence> allMyEvidenceArrayList = new ArrayList<>();

    public Angels_Evidence_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyEvidenceFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myEvedenceAdapter = new MyEvedenceAdapter(myEvidenceArrayList, new EvdClickListener() {
            @Override
            public void onPlayClick(MyEvidence evidence) {

            }

            @Override
            public void onDeleteClick(MyEvidence evidence, int pos) {
                Utility.Companion.hideKeyboard(requireActivity());
                deleteData(evidence, pos);
            }
        });
        binding.myEvRv.setAdapter(myEvedenceAdapter);

        loadDataFromServer();

        searchListener();
    }

    private void searchListener() {
        binding.searchEd.addTextChangedListener(new TextWatcher() {
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
        myEvidenceArrayList.clear();

        if (keyword.isEmpty()) {
            myEvidenceArrayList.addAll(allMyEvidenceArrayList);
        } else {
            for (MyEvidence evidence : allMyEvidenceArrayList) {
                if (evidence.file != null && evidence.file.startsWith(keyword)) {
                    myEvidenceArrayList.add(evidence);
                }
            }
        }
        myEvedenceAdapter.notifyDataSetChanged();
    }


    private void deleteData(MyEvidence evidence, int pos) {
        binding.progress.setVisibility(View.VISIBLE);
        SharedPreferences preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String userID = preferences.getString("userId", " ");
        Call<JsonObject> call;
        if (evidence.format.equals("mp3")) {
            call = RetrofitClientInstance.getInstance().getApi().deleteAudioEvd(userID, String.valueOf(evidence.id));
        } else {
            call = RetrofitClientInstance.getInstance().getApi().deleteVideoEvd(userID, String.valueOf(evidence.id));
        }
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(requireActivity(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                    allMyEvidenceArrayList.remove(pos);
                    binding.searchEd.setText("");
                    myEvidenceArrayList.clear();
                    myEvidenceArrayList.addAll(allMyEvidenceArrayList);
                    myEvedenceAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadDataFromServer() {
        binding.noDataFound.setVisibility(View.GONE);
        binding.progress.setVisibility(View.VISIBLE);
        SharedPreferences preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String userID = preferences.getString("userId", " ");
        Call<MyEvidenceResponse> call = RetrofitClientInstance.getInstance().getApi().angelEvidence(userID);

        call.enqueue(new Callback<MyEvidenceResponse>() {
            @Override
            public void onResponse(Call<MyEvidenceResponse> call, Response<MyEvidenceResponse> response) {
                binding.progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    MyEvidenceResponse res = response.body();
                    if (res != null && res.getData() != null) {
                        myEvidenceArrayList.clear();
                        allMyEvidenceArrayList.clear();
                        myEvidenceArrayList.addAll(res.data);
                        allMyEvidenceArrayList.addAll(res.data);
                        myEvedenceAdapter.notifyDataSetChanged();
                    }
                }

                if (myEvidenceArrayList.isEmpty()) {
                    binding.noDataFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MyEvidenceResponse> call, Throwable t) {

            }
        });
    }
}