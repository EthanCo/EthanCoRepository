##Dagger2使用之注解获取工具类
### 首先 ###
在ModelComponent中增加一个方法，其中AModel为之后要注入的Model

	void inject(AModel model); //除了AModel，其他的都不需要变

### 然后 ###
AModel继承BaseModel并实现initInject方法，在方法中写上下面这行代码，完成注入

	component.inject(this);

### 最后 ###
在AModel中添加通过@Inject注解来添加成员变量，比如

	@Inject
   	Gson gson;

然后就可以直接使用Gson了。

这个gson是全局唯一的，单例的  

具体实现在ModelModule类中，ModelModule就相当于一个工厂，提供全局的所有的工具类，统一初始化及便于使用

	@Singleton //该注解会使该类为单例，需配合Dagger2使用
	@Module //说明是个Module，可以有多个Module，通过include来依赖
	public class ModelModule {
	
	    @Provides
	    public Gson provideGson() { //提供Gson，之后再Dagger2注入的地方可以通过注解直接获取到
	        return new Gson();
	    }
	}  


### 增加新的工具类 ###
比如，增加Retrofit  

只需，在ModelModule中新增@Provides注解的一个方法即可

    @Provides
    public Retrofit provideRetrofit() {                 //一般方法名规范为provide开头
        return new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }

然后

在AModel中，通过

	@Inject
	Retrofit retrofit;

获取即可

### 其他 ###
> 还有其他的，比如MVP之间的依赖通过Dagger2来实现等，不能保证稳定，还需要时间的检验。  