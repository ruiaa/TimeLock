# TimeLock
控制 android app 的使用时段， MVVM DataBinding

##	具体功能
请看早期版本[Lock](https://github.com/ruiaa/Lock)

##	结构
长期驻后台的监控进程 + 显示界面的主进程

1.	common	通用
2.	monitor	后台监控
3.	main	界面

###	MVVM实践
+	main
	+	entity	所有封装数据的实体
	+	event	+Rubus 传递消息
	+	model	从后台获取数据
	+	modules	根据界面功能划分模块(View+ViewModel) 


###	如何基于DataBinding解耦/模板化View+ViewModel
1.	View : Activity,Fragment

	ViewModel : ActivityModel,FragmentModel

2.	View	只关注UI变换，不持有数据引用
	+	onCreate	创建ViewModel,传递原始数据
	+	onCreateView/inflate  获取Databinding，绑定ViewModel+XML
	+	onDestroy	销毁ViewModel

3.	ViewModel	只关注data的获取、变换、分发，持有数据引用
	+	getXxx
	+	setXxx
	+	destroy	由View调用，销毁所有引用
4.	所有要显示的数据由ViewModel提供
	+	View.onCreateView	binding.setValue(BR.xxxModel,xxxModel);
	+	XML "@{xxxModel.getXxx()}"	
	+	xml只绑定xxxModel???还是绑定所有需要的entity???
5.	监听器绑定
	+	修改数据
		+	XML 	xxxModel.getEntity.setter 
		+	XML 	xxxModel.set/saveXxx()
		+	View	xxxModel.setXxx()

	+	视图变换
		+	简单效果翻转变换		XML
		+	界面跳转/打开/关闭		View
		+	界面跳转+数据保存		View+xxxModel.setXxx()
6.	adapter
	+	? 由View创建，传递view.xxxModel
	+	类似VIew处理，inflate时获取DataBinding，getItemView时绑定数据
	+	监听器
		+	修改数据	xml，xxxModel
		+	视图变换	涉及View
			+	??在constructor传入View，在adapter内调用View的方法设置监听器
			+	??adapter内部定义并调用监听器接口，在View内实现并设置接口
	
	+	? 归为ViewModel层
		+	只绑定xxxModel，绑定只涉及xxxModel修改数据的监听器
		+	显示交给Xml，视图变换监听器通过接口交给View

7.	View与ViewModel线程切换
	+	ViewModel只处理数据，同步获取数据，不关心线程问题
	+	View认为调用的ViewModel方法可能耗时长，可通过Rx切换线程异步调用

