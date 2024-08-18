:{TOC}

# Compose可组合项知识

#### 1. 可组合项介绍
    用于将多个UI组件组合在一起，以便在一个单独的组件中显示它们。使用@Composable注解来标记一个函数，该函数将返回一个UI组件。这个函数可以包含其他UI组件，这些UI组件可以是其他可组合项函数，也可以是Android的现有UI组件。

#### 2. 通过@Preview注解，可以在Android Studio中预览可组合项函数的UI效果。

#### **3. AndroidView和ComposeView**

是两个用于在可组合项中显示现有Android视图的组件。
- AndroidView是一个通用的组件，可以用于显示任何Android视图，
- 而ComposeView是一个专门用于显示Compose UI的组件。

AndroidView的使用

```kotlin
@Composable
fun MyComposable() {
    Column {
        Text("Hello, world!")
        AndroidView(factory = { context ->
            //inflate an existing Android view from layout.xml
            val view = LayoutInflater.from(context).inflate(R.layout.layout, null)
            //to do something with the view
            val composeView = view.findViewById<ComposeView>(R.id.composeView)
            composeView.setContent {//set the content of the ComposeView
                MyComposable()
            }
            return@AndroidView view
        })
    }
}
```

ComposeView的使用

```xml
<?xml version="1.0" encoding="utf-8"?><!--layout.xml-->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content" android:layout_height="wrap_content"
    android:orientation="vertical">

    <androidx.compose.ui.platform.ComposeView android:id="@+id/composeView"
        android:layout_width="wrap_content" android:layout_height="wrap_content">

    </androidx.compose.ui.platform.ComposeView>
</LinearLayout>
```

```kotlin
//Activity.kt
//...
override onCreate (savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        MyComposable()
    }
}
//...

```

#### 4. 可组合项组件库
- Conlumn: 垂直布局
- Row: 水平布局
- Box: 用于放置其他组件的容器
- Scaffold: 用于创建一个基本的Material Design布局，包含一个应用栏、一个主要内容区域和一个底部导航栏。
- LazyColumn: 用于显示大量数据的垂直滚动列表
- LazyRow: 用于显示大量数据的水平滚动列表
- LazyVerticalGrid: 用于显示大量数据的垂直滚动网格
- LazyHorizontalGrid: 用于显示大量数据的水平滚动网格
- ScrollableColumn: 用于显示可以滚动的垂直列
- ScrollableRow: 用于显示可以滚动的水平行
- ScrollableTabRow: 用于显示可以滚动的选项卡
- ScrollablePositionRow: 用于显示可以滚动到指定位置的行
- TabRow: 用于显示选项卡
- Tab (TabRow.Tab): 用于显示单个选项卡
- TopAppBar: 用于显示应用栏
- NavigationBar: 用于显示底部导航栏
- BottomAppBar: 用于显示底部应用栏
- NavigationRail: 用于显示导航栏
- Drawer: 用于显示抽屉式导航栏
- ModalDrawerLayout: 用于显示模态抽屉式导航栏
- LaunchedEffect: 用于在可组合项中启动协程
- rememberCoroutineScope: 用于在可组合项中记住协程范围

#### **5. 可组合项修饰符**

- Modifier.padding: 用于设置组件的内边距
- Modifier.background: 用于设置组件的背景颜色
- Modifier.border: 用于设置组件的边框
- Modifier.fillMaxWidth: 用于设置组件的宽度为最大宽度
- Modifier.fillMaxHeight: 用于设置组件的高度为最大高度
- Modifier.fillMaxSize: 用于设置组件的大小为最大大小
- Modifier.wrapContentSize: 用于设置组件的大小为包裹内容大小
- Modifier.wrapContentWidth: 用于设置组件的宽度为包裹内容宽度
- Modifier.wrapContentHeight: 用于设置组件的高度为包裹内容高度
- Modifier.size: 用于设置组件的大小
- Modifier.offset: 用于设置组件的偏移量
- Modifier.clickable: 用于设置组件的点击事件
- Modifier.clip: 用于设置组件的裁剪效果
- Modifier.shadow: 用于设置组件的阴影效果

#### **6. 可组合项的动画**

- 使用Transition组件可以在可组合项中添加动画效果。Transition组件可以在两个状态之间进行动画过渡，例如从可见到不可见，或者从不可见到可见。
- Modifier.animateContentSize: 用于在可组合项的大小发生变化时添加动画效果。
- Modifier.animate: 用于在可组合项的属性发生变化时添加动画效果。
- animateColorAsState: 用于在颜色发生变化时添加动画效果。
- animateDpAsState: 用于在dp值发生变化时添加动画效果。

animateDpAsState示例：

```kotlin
  val expandExtraPadding by animateDpAsState(
    targetValue = if (isExpanded) 16.dp else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
)
```

- animateFloatAsState: 用于在浮点数值发生变化时添加动画效果。
- animate*AsState: 用于在其他类型的值发生变化时添加动画效果。

```kotlin
@Composable
fun MyComposable() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Gray)
            .border(1.dp, Color.Black)
    ) {
        Text("Hello, world!")
    }
}
```
- AnimatedVisibility 用于在可组合项的可见性发生变化时添加动画效果。
```kotlin
AnimatedVisibility(extended) {
    Text(
        text = stringResource(R.string.edit),
        modifier = Modifier
            .padding(start = 8.dp, top = 3.dp)
    )
}
```