package com.application.refinary.services;

import retrofit2.Response;

public interface ApiListener {
    <ResponseType> void  success(Response<ResponseType> response, String apiCallName);

    void onErrorListner();
}
