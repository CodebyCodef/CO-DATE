package API;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:5025/"; // Emülatör için API URL'si

    public static Retrofit getInstance() {
        if (retrofit == null) {
            // Logging Interceptor ekle
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttpClient yapılandır
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor) // İstek ve yanıt loglama
                    .connectTimeout(30, TimeUnit.SECONDS) // Bağlantı zaman aşımı
                    .readTimeout(30, TimeUnit.SECONDS)    // Okuma zaman aşımı
                    .writeTimeout(30, TimeUnit.SECONDS)   // Yazma zaman aşımı
                    .build();

            // Retrofit'i oluştur
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // JSON dönüştürücü
                    .client(client) // Özel OkHttp istemcisini bağla
                    .build();
        }
        return retrofit;
    }
}
