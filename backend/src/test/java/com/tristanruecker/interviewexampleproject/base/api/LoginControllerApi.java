package com.tristanruecker.interviewexampleproject.base.api;

import com.tristanruecker.interviewexampleproject.models.objects.entities.User;
import com.tristanruecker.interviewexampleproject.models.objects.entities.UserEmailAndPassword;
import com.tristanruecker.interviewexampleproject.models.response.UserLoggedInResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginControllerApi {


    @POST(value = "register")
    Call<UserLoggedInResponse> register(@Body User user);

    @POST(value = "login")
    Call<UserLoggedInResponse> userLogin(@Body UserEmailAndPassword userEmailAndPassword);

}
