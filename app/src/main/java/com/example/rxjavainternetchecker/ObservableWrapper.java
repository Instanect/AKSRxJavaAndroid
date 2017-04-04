package com.example.rxjavainternetchecker;

import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by AKS on 4/4/2017.
 */

public class ObservableWrapper {


    private final HttpCaller caller;

    public ObservableWrapper(HttpCaller caller) {

        this.caller = caller;
    }

    public Observable<String> getObservable() {

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
        });

    }

}
