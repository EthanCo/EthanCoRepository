# RxJava之lift()变换原理及实现自定义 Operator #

map，flatMap等实质上都是针对事件序列的处理和再发送。而在 RxJava 的内部，它们是基于同一个基础的变换方法： lift(Operator)

## 需要了解的一些知识 ##
Observer 是 Subscriber 的抽象父类，Subscriber只是比Observer 多了onStart()，unsubscribe()等方法。  
在 RxJava 的 subscribe 过程中，Observer 也总是会先被转换成一个 Subscriber 再使用。所以如果你只想使用基本功能，选择 Observer 和 Subscriber 是完全一样的。



- onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行）， onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，具体可以在后面的文中看到。


- unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。在这个方法被调用后，Subscriber 将不再接收事件。一般在这个方法调用前，可以使用 isUnsubscribed() 先判断一下状态。 unsubscribe() 这个方法很重要，因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，这个引用如果不能及时被释放，将有内存泄露的风险。所以最好保持一个原则：要在不再使用的时候尽快在合适的地方（例如 onPause() onStop() 等方法中）调用 unsubscribe() 来解除引用关系，以避免内存泄露的发生。

##调用lift方法示例  

原来的Observable:  

        Observable<String> originObservalbe = Observable.create(originOnSubscribe);

目标Observable:

        Observable<Integer> targetObservable;

operator:将String类型转化为Integer的Observable.Operator

		Observable.Operator operator = new Observable.Operator<Integer, String>() {
	
	            @Override
	            public Subscriber<? super String> call(Subscriber<? super Integer> subscriber) {
	                return new Subscriber<String>() {
	                    @Override
	                    public void onCompleted() {
	                        subscriber.onCompleted();
	                    }
	
	                    @Override
	                    public void onError(Throwable e) {
	                        subscriber.onError(e);
	                    }
	
	                    @Override
	                    public void onNext(String s) {
	                        int value = Integer.valueOf(s); //进行转换
	                        subscriber.onNext(value);
	                    }
	                };
	            }
	        }

通过 lift 进行转化:

        Observable<Integer> targetObservable = originObservalbe.lift(operator);


即通过该operator将originObservalbe转化为Integer类型的targetObservable
	

## 来看看lift简化版源码

	public final <R> Observable<R> lift(final Operator<? extends R, ? super T> operator) {
	        return new Observable<R>(new OnSubscribe<R>() {
	            @Override
	            public void call(Subscriber<? super R> o) {
	                try {
	                    Subscriber<? super T> st = operator.call(o); //即执行operator，转化为我们想要的Subscriber
	                    try {
							//onStart是 Subscriber 相比Observer增加的方法。它会在 subscribe 刚开始，
							//而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。
	                        st.onStart();
							//st即我们通过operator转化后的Subscriber，
							//onSubscribe是调用lift()方法之前的那个Observable的OnSubscribe，即前面的targetObservable.onSubscribe
	                        onSubscribe.call(st); 
	                    } catch (Throwable e) {
	                        Exceptions.throwIfFatal(e);
	                        st.onError(e);
	                    }
	                } catch (Throwable e) {
	                    Exceptions.throwIfFatal(e);
	                    o.onError(e);
	                }
	            }
	        });
	    }

##举个实现的例子

将String类型的值转化为Integer类型的值乘以2并返回

	Observable.just("123").lift(new Observable.Operator<Integer, String>() {
	
	            @Override
	            public Subscriber<? super String> call(Subscriber<? super Integer> subscriber) {
	                return new Subscriber<String>() {
	                    @Override
	                    public void onCompleted() {
	                        Log.i("Z-MainActivity", "onCompleted: ");
	                        subscriber.onCompleted();
	                    }
	
	                    @Override
	                    public void onError(Throwable e) {
	                        Log.e("Z-MainActivity", "onError: " + e.getMessage());
	                        subscriber.onError(e);
	                    }
	
	                    @Override
	                    public void onNext(String s) {
	                        Log.i("Z-MainActivity", "onNext: s:" + s);
	                        int value = Integer.valueOf(s) * 2; //转化为Integer类型的值并乘以2
	                        subscriber.onNext(value);
	                    }
	                };
	            }
	        }).subscribe(new HastesSubscriber<Integer>() {
	            @Override
	            public void onNext(Integer integer) {
	                Log.i("Z-MainActivity", "onNext: END:" + integer);
	            }
	        });

输出结果:  

	Z-MainActivity: onNext: s:123
	Z-MainActivity: onNext: END:246

## 其他 ##

> RxJava 不建议开发者自定义 Operator 来直接使用 lift()，而是建议尽量使用已有的 lift() 包装方法（如 map() flatMap() 等）进行组合来实现需求，因为直接使用 lift() 非常容易发生一些难以发现的错误。  
> 讲述 lift() 的原理只是为了更好地了解 RxJava ，从而可以更好地使用它。


> 参考  
> [http://gank.io/post/560e15be2dca930e00da1083](http://gank.io/post/560e15be2dca930e00da1083)