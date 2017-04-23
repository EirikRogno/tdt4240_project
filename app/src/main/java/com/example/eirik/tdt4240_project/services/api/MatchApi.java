package com.example.eirik.tdt4240_project.services.api;

import com.example.eirik.tdt4240_project.models.Match;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.*;

/**
 * Created by Sindre on 4/23/2017.
 */

public interface MatchApi {

    @GET("match/{userID}")
    Observable<List<Match>> getMatchesForUser(@Path("userID") String username);

    @POST("match")
    Observable<Match> saveNewMatch(@Body String matchJson);

    @DELETE("match/{matchId}")
    Observable<String> removeMatch(@Path("matchId") String matchId);

    @PUT("match/{matchID}")
    Observable<Match> updateMatchState(@Path("matchID") String matchId, @Query("state") String newState);
}
