package com.application.refinary.retrofit;

import static org.apache.http.conn.ssl.SSLSocketFactory.SSL;

import android.annotation.SuppressLint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientServiceGenerator {
    //private static final String ROOT_URL = "https://demo.mobisprint.com:4003/";

    private static final String ROOT_URL = "https://exoneapi.refineryhotelnewyork.com:4000/";

    /*public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try{
            @SuppressLint("CustomX509TrustManager") final TrustManager[] trustCerts = new TrustManager[]{
             new X509TrustManager() {
                 @SuppressLint("TrustAllX509TrustManager")
                 @Override
                 public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                 }

                 @SuppressLint("TrustAllX509TrustManager")
                 @Override
                 public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                 }

                 @Override
                 public X509Certificate[] getAcceptedIssuers() {
                     return new X509Certificate[0];
                 }
             }
            };
            final SSLContext Context = SSLContext.getInstance("SSL");

            Context.init (null, trustCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = Context.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory,(X509TrustManager) trustCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
            }catch (Exception e){
            throw new RuntimeException(e);
        }
    }*/

        private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build();

    private static Retrofit builder =
            new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(new ToStringConverterFactory())
                    .client(httpClient)
                    .build();



    public ClientServiceGenerator() throws NoSuchAlgorithmException {
    }

    //   private static Retrofit retrofit = builder.build();

    public static Retrofit getUrlClient() {
        return builder;
    }

    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return (Converter<ResponseBody, Object>) body -> {
                // Utility.Log("VMA","Response  Body "+body.contentLength());
                if (body.contentLength() == 0) return null;
                return delegate.convert(body);
            };
        }
    }

}
