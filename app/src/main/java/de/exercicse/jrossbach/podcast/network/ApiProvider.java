package de.exercicse.jrossbach.podcast.network;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class ApiProvider {

    private String baseUrl;
    private Retrofit retrofit;

    public static Retrofit getRetrofit(final String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
                        new Persister(new AnnotationStrategy()
                        )))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static PodcastApi getChannelApi(final String baseUrl){
        return getRetrofit(baseUrl).create(PodcastApi.class);
    }
}
