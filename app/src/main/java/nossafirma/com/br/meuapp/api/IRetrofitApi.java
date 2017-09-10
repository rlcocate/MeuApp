package nossafirma.com.br.meuapp.api;

import nossafirma.com.br.meuapp.model.Login;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IRetrofitApi {

    @GET("v2/58b9b1740f0000b614f09d2f")
    Call<Login> getDefaultAutentication();
}
