package com.harish

import rx.lang.scala.{Observable, Subscription}

/**
 * @author harish.sharma
 */
object ScalaFun {

  def nothing: Observable[Nothing] = Observable[Nothing](observer => {
    Subscription()
  })

  def error[T](e: Throwable): Observable[T] = Observable[T](observer => {
    observer.onError(e)
    Subscription()
  })

  def startsWith[T](ss: T*): Observable[T] = {
    Observable[T](observer => {
      for (s <- ss) observer.onNext(s)
      Subscription.apply(observer)
    })
  }

  def filter[T](f: T => Boolean): Observable[T] = {
    null
  }

  def main(args: Array[String]) {
    val nums = List(1, 2, 3, 4)
    val obs: Observable[Int] = fun(nums)
    obs.subscribe(num => println(num))
  }

  def fun(col: List[Int]): Observable[Int] = {
    Observable.from(col)
  }
}
