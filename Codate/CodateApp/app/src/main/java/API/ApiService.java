package API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// Web API'deki Register endpoint'i için Retrofit tanımı
public interface ApiService {

    @POST("api/Register/Register") // Web API'deki Register endpoint'i
    Call<Void> registerUser(@Body Register user); // Kullanıcı kaydı için POST isteği
}
