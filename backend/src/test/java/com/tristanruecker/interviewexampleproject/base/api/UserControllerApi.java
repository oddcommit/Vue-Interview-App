package com.tristanruecker.interviewexampleproject.base.api;

import com.tristanruecker.interviewexampleproject.models.objects.entities.User;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface UserControllerApi {

    @GET(value = "users")
    Call<List<User>> getAllUsersWithourAuthorization();

}
