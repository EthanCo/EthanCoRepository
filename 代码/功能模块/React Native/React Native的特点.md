# React Native的特点 #
## 原生组件 ##
使你的App获得平台一致的视觉效果和体验 !  (通过使用对应的React component)  

## 异步执行 ##
在JavaScript代码和原生平台之间的所有操作都是异步执行的，并且原生模块还可以根据需要创建新的线程。

> 你可以在主线程解码图片，然后在后台将它保存到磁盘，或者在不阻塞UI的情况下计算字体大小和界面布局等等。  

	流畅、反应敏捷。  

可以借助Chrome开发者工具去调试应用(JavaScript和原生代码之间的通信是完全可序列化的)  

## 触摸事件处理 ##
React Native 实现了一个强大的触摸事件处理系统，可以在复杂的View层次关系下正确地处理触摸事件。同时还提供了高度封装的组件如TouchableHighlight等，可以直接嵌入到ScrollView或者其他的元素中，无需额外配置。  

## 弹性盒(Flexbox)和样式 ##
view布局借鉴了Flexbox模型，Flexbox让大多数常见的UI布局构建变得简单  
RN还支持多种常见的Web样式，例如fontWeight等  

## 兼容通用标准 ##
React Native致力于改进视图代码的编写方式。还吸纳了Web生态中的通用标准，并在必要的时候为这些API提供兼容层。 

	使得npm上的许多库就可以在React Native中直接使用  

> 这样的兼容库有XMLHttpRequest, window.requestAnimationFrame, navigator.geolocation等  

## 扩展性 ##
使用React Native，无需编写一行原生代码即可创造一款不错的app。尽管如此，使用自定义的原生视图和模块来扩展React Native也非常容易 —— 这意味着你现有的所有工作都可以被复用，你喜欢的各种原生库都可以被导入。  

### 创建Android模块 ###
####创建一个基础的Android模块
创建一个继承自ReactContentBaseJavaModule的类，然后使用@ReactMethod标注(Annotation)来标记那些你希望通过Javascript来访问的方法。最后，需要在ReactPackage中注册这个模块。  

	// Java

	public class MyCustomModule extends ReactContextBaseJavaModule {
	
	// Available as NativeModules.MyCustomModule.processString
	  @ReactMethod
	  public void processString(String input, Callback callback) {
	    callback.invoke(input.replace("Goodbye", "Hello"));
	  }
	}

#####

	// JavaScript
	
	import React, {
	  Component,
	} from 'react';
	import {
	  NativeModules,
	  Text
	} from 'react-native';
	class Message extends Component {
	  constructor(props) {
	    super(props);
	    this.state = { text: 'Goodbye World.' };
	  },
	  componentDidMount() {
	    NativeModules.MyCustomModule.processString(this.state.text, (text) => {
	      this.setState({text});
	    });
	  }
	  render() {
	    return (
	      <Text>{this.state.text}</Text>
	    );
	  }
	}  

#### 创建Android View ####

创建自定义的Android View，首先定义一个继承自SimpleViewManager的类，并实现createViewInstance和getName方法，然后使用@ReactProp标注导出属性，最后用一个Javascript文件连接并进行包装。  

	// Java

	public class MyCustomViewManager extends SimpleViewManager<MyCustomView> {
	  @Override
	  public String getName() {
	    return "MyCustomView";
	  }
	
	  @Override
	  protected MyCustomView createViewInstance(ThemedReactContext reactContext) {
	    return new MyCustomView(reactContext);
	  }
	
	  @ReactProp(name = "myCustomProperty")
	  public void setMyCustomProperty(MyCustomView view, String value) {
	    view.setMyCustomProperty(value);
	  }
	}

#####

	// JavaScript
	
	import React, {
	  Component,
	  requireNativeComponent 
	} from 'react-native';
	
	var NativeMyCustomView = requireNativeComponent('MyCustomView', MyCustomView);
	
	export default class MyCustomView extends Component {
	  static propTypes = {
	    myCustomProperty: React.PropTypes.oneOf(['a', 'b']),
	  };
	  render() {
	    return <NativeMyCustomView {...this.props} />;
	  }
	}

> 摘录自 [http://reactnative.cn/](http://reactnative.cn/)