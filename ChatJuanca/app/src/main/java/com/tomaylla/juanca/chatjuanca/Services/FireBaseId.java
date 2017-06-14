package com.tomaylla.juanca.chatjuanca.Services;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by KevinPiazzoli on 09/02/2017.
 */

public class FireBaseId extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }

}
