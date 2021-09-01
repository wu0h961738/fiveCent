package com.venom.backend.http;

import com.venom.backend.utils.net.AuthService;
import retrofit2.Call;

import java.io.IOException;
import java.util.function.Function;

/**
 * <p>HTTP request execution<p>
 */
public final class ClientToService {

    /** line auth base url*/
    private static String BASE_URL_LINE = "https://api.line.me/";

    /**取得line api service*/
    public static <T, R> R getLineService(final Class<T> service,
                                 final Function<T, Call<R>> function) throws IOException {
        return getClient(BASE_URL_LINE, service, function);
    }

    /**
     * <p>execute HTTP requests</p>
     */
    private static <T, R> R getClient(
            final String url,
            final Class<T> service,
            final Function<T, Call<R>> function) throws IOException {

        T t = AuthService.createService(service, url);
        Call<R> call = function.apply(t);
        return call.execute().body();
    }
}
