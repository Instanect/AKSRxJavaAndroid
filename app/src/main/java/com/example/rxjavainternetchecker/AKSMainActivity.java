package com.example.rxjavainternetchecker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AKSMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpCaller caller = new HttpCaller(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);

/*        progressDialog.setMessage("Getting data from Google...");
        progressDialog.show();
        caller.getObservable().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("Str", s);
                progressDialog.dismiss();
            }
        });
        ;
    */


        FlatMap flatMap = new FlatMap(caller);
        flatMap.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("Str", "abcd");
                    }
                });

    }
}
