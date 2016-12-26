## React Native 笔记 ##

### 最简单的代码 ###

	import React, { Component } from 'react';
	import { AppRegistry, Text } from 'react-native';
	
	class HelloWorldApp extends Component {
	  render() {
	    return (
	      <Text>Hello world!</Text>
	    );
	  }
	}
	
	// 注意，这里用引号括起来的'HelloWorldApp'必须和你init创建的项目名一致
	AppRegistry.registerComponent('HelloWorldApp', () => HelloWorldApp);


> AppRegistry模块用来告知React Native哪一个组件被注册为整个应用的根布局。  

### Props(属性) ###

大多数组件在创建时就可以使用各种参数来进行定制。用于定制的这些参数就称为props（属性）。  

自定义组件也可以使用props，只需在render函数中引入this.props，然后按需处理即可  

### 状态 ###
我们使用两种数据来控制一个组件: props(属性)和state(状态)。props是在父组件中指定，而一经制定，在被指定的组建的声明周期则不会改变。对于需要改变的数据，我们需要使用state。  

一般来说，你需要在constructor中初始化state，然后需要修改时调用setState方法。  

## 高度和宽度 ##

#### 指定宽高 ####
最简单的给组件设定尺寸的方式就是在样式中指定固定的width和height。  

这样给组件设置尺寸也是一种常见的模式，比如要求在不同尺寸的屏幕上显示成一样的大小。  

#### Flex(弹性)宽高 [权重] ####
在组件样式中使用flex可以使其在可利用的空间中动态地扩张或收缩。  

一般而言我们会使用flex:1来指定某个组件扩张以撑满所有剩余空间。    

如果有多个并列的子组件使用了flex:1，则这些子组件会平分父容器中剩余的空间。  

如果这些并列的子组件的flex值不一样，则谁的值更大，谁占据剩余控件的比例就更大  

> 组件能够撑满剩余空间的前提是其父容器的尺寸不为零。  
> 如果父容器即没有固定的width和height，也没有设定flex，则父容器的尺寸为0。  
> 其子组件如果使用了flex，也是无法显示的。  

#### 使用Flexbox布局 ####

我们在React Native 中使用flexbox规则来指定某个组件的子元素的布局。Flexbox可以在不同屏幕尺寸上提供一致的布局结构。  

一般来说，使用flexDirection、alignItems和justifyContent 三个样式属性就已经能满足大多数布局需求。  

> React Native的Flexbox的工作原理和web上的CSS基本一致，当然也存在少许差异。  
> 默认不同: flexDirection的默认值是column而不是row，alignItems的默认值是stretch而不是flex-start，以及flex只能指定一个数字值。

#### Flex Direction ####

在组件的style中指定flexDirection 可以决定布局的**主轴**  
row:水平轴方向排列  
column:竖直轴方向排列 (默认)  

#### Justify Content ####
决定子元素沿着主轴的排列方式  

flex-start，center，flex-end，space-around以及space-between  

flex-start:居左(row)/居上(column)  
flex-end:居右(row)/局下(column)  
center:水平居中(row)/垂直居中(column)  
space-around:
space-between:  

[详情点这里](http://sources.ikeepstudying.com/flexbox/justify-content.html)  

#### Align Items ####
决定其子元素沿着次轴 (与主轴垂直的轴，比如主轴方向为row，则次轴方向为column)的排序方式  
flex-start、center、flex-end以及stretch。
flex-start:居上(row)/居左(column)  
filx-end:居下(row)/局右(column)  
center:垂直居中(row)/水平居中(column)  
stretch:项目被拉伸以适应容器

> 注意：要使stretch选项生效的话，子元素在次轴方向上不能有固定的尺寸。以下面的代码为例：只有将子元素样式中的width: 50去掉之后，alignItems: 'stretch'才能生效。

[详见](http://www.runoob.com/cssref/css3-pr-align-items.html)  

### ListView ###
与ScrollView不同，List并不会立即渲染所有元素，而是优先渲染屏幕上可见的元素。  

ListView组件必须的两个属性是dataSource和renderRow，dataSource是列表的数据源，而renderRow则逐个解析数据源中的数据，然后返回一个设定好格式的组件来渲染。  

> rowHasChanged 函数也是ListView的必须属性。这里我们只是简单的比较两行数据是否是同一个数据 (===符号只比较基本类型数据的值，和引用类型的地址)来判断某行数据是否变化了。  

