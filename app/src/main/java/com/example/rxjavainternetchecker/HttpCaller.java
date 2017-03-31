package com.example.rxjavainternetchecker;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.ExecutionException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Basic implentation of volley, rxandorid and rxjava
 */

public class HttpCaller {

    private Context context;

    public HttpCaller(Context context) {

        this.context = context;
    }

    public Flowable<String> getObservable() {


        return Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> source) throws Exception {
                try {
                    String data = getData();
                    source.onNext(data);
                    source.onComplete();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    source.onError(e);
                }
            }
        }, BackpressureStrategy.MISSING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }

    private String getData() throws ExecutionException, InterruptedException {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.google.com";

        RequestFuture<String> future = RequestFuture.newFuture();


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, future, future);
// Add the request to the RequestQueue.
        queue.add(stringRequest);

        return future.get();
    }
}
