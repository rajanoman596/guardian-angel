package com.stackbuffers.myguardianangels.interfaces;

import com.stackbuffers.myguardianangels.Models.myEvidence.MyEvidence;

public interface EvdClickListener {
    void onPlayClick(MyEvidence evidence);
    void onDeleteClick(MyEvidence evidence,int pos);
}
