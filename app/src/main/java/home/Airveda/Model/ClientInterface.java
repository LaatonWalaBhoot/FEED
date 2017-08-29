package home.airveda.Model;

import java.util.ArrayList;

import home.airveda.Model.Details;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Aishwarya on 8/29/2017.
 */

public interface ClientInterface {

    @GET("/bins/x5279")
    Call<ArrayList<Details>> getDetails();
}
