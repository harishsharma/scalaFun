package com.harish;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author harish.sharma
 */
public class JavaFun {

    public static Observable<Integer> obs(List<Integer> list) {

        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    for (Integer i : list) {
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static void main(String[] arg) {
        List<Integer> list = new ArrayList() {{
            add(1);
            add(2);
        }};
        Observable<Integer> listObs = obs(list);

        Subscriber<Integer> subs = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Finished");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Exception " + e);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Thread " + Thread.currentThread().getName() + " : int " + integer);
            }
        };
        listObs.subscribe(subs);

        Observable<Integer> map = listObs.map(new Func1<Integer, Integer>() {

            @Override
            public Integer call(Integer integer) {
                return integer * 2;
            }
        });

        map.subscribe(subs);

    }
}
