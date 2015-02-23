package com.harish;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author harish.sharma
 */
public class JavaFun {

    public static <T> Observable<T> obs(List<T> list) {

        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    for (T i : list) {
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static <T> Observable<T> nothing() {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                //do nothing
            }
        });
    }

    public static <T> Observable<T> error(Throwable e) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onError(e);
            }
        });
    }

    public static <T> Observable<T> startsWith(List<T> items, Observable<T> orig) {

        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                for (T item : items)
                    subscriber.onNext(item);
                orig.subscribe(subscriber);
            }
        });
    }

    public static <T> Observable<T> filter(Function<T, Boolean> predicate, Observable<T> orig) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                System.out.println("Before Before");
                //incomplete
            }
        });
    }


    public static <T, S> Observable<S> map(Function<T, S> mapper, Observable<T> orig) {
        return null;
    }


    public static void main(String[] arg) {
        List<Integer> list = new ArrayList() {{
            add(1);
            add(2);
            add(3);
            add(4);
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

        Observable<Integer> map = listObs.map(new Func1<Integer, Integer>() {

            @Override
            public Integer call(Integer integer) {
                return integer * 2;
            }
        });

//        listObs.subscribe(subs);
//        map.subscribe(subs);
//        Observable<Integer> nothing = nothing();
//        nothing.subscribe(subs);
//        Observable<Integer> error = error(new NullPointerException());
//        error.subscribe(subs);
//        Observable<Integer> startsWith = startsWith(new ArrayList<Integer>() {{
//            add(11);
//            add(12);
//        }}, error);
//        startsWith.subscribe(subs);
        Observable<Integer> filter = filter(t -> t % 2 == 1, listObs);
        filter.subscribe(subs);
    }
}
