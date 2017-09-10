package nossafirma.com.br.meuapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String API_URL_BASE = "http://www.mocky.io/";

    public static IRetrofitApi getApiData() {

        // Verifica se não há instância.
        if (retrofit == null) {

            // Instancia e configura novo Retrofit.
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(IRetrofitApi.class);
    }

}
