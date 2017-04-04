package com.example.rxjavainternetchecker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.ExecutionException;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;


/**
 * Created by AKS on 4/3/2017.
 */

public class FlatMap {

    private HttpCaller caller;

    public FlatMap(HttpCaller caller) {


        this.caller = caller;
    }

    public Observable<Object> getObservable() {


        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    String data = caller.getData();
                    e.onNext(data);
                    e.onComplete();
                } catch (ExecutionException | InterruptedException exception) {
                    exception.printStackTrace();
                    e.onError(exception);
                }
            }
        }).flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                return caller.getObservableNext();
            }
        });
    }


}
