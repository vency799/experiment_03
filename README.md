## 实验三



实验内容：完成Android UI组件相关用法

实验步骤：

1、Android ListView的用法：利用SimpleAdapter实现

![image](https://github.com/vency799/experiment_03/blob/master/Listview_exp1.png)

activity_main_02.xml文件设置ListView属性：

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">
    <ListView
        android:id="@+id/mylist"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clickable="true"
        android:divider="@color/dark"
        android:dividerHeight="1dp"
        android:fastScrollEnabled="true"
        android:cacheColorHint="@android:color/transparent">
    </ListView>
</LinearLayout>
```

*clickable：true为可点击，设置点击事件*

*divider：下边线颜色；dividerHeight：下边线宽度*

*fastScrollEnabled：listview中的快速滑动属性，大于4页时才会体现功能，这里可设置可不设置*

*cacheColorHint：去除listview的背景颜色；@android:color/transparent：背景颜色设置为 透明*



simple_adapter_test.xml：

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:padding="10dp"
    android:background="@drawable/list_item_color_bg">
    //左边生成动物名字

    <TextView
        android:id="@+id/animal_name"
        android:layout_width="314dp"
        android:layout_height="71dp"
        android:gravity="center_vertical|left"
        android:textColor="#000000"
        android:textSize="24sp" />
    //右边生成动物图片

    <ImageView
        android:id="@+id/animal_pic"
        android:layout_width="79dp"
        android:layout_height="71dp"
        android:padding="10dp" />
</LinearLayout>
```

文件中设置了" animal_name "、" animal_pic "两个id，在后面的MainActivity.java中用到。

样式：

![image](https://github.com/vency799/experiment_03/blob/master/simadp_layout.png)

MainActivity.java:

```java
package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class MainActivity extends AppCompatActivity {
    public String[] names = new String[] {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    public int[] images = new int[] {R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
    R.drawable.dog, R.drawable.cat, R.drawable.elephant};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_02);
        //输出内容
        List<Map<String, Object>> listitems = new ArrayList<>();
        for(int i = 0; i < names.length; i++){
            Map<String, Object> listitem = new HashMap<>();
            listitem.put("animal_name", names[i]);
            listitem.put("animal_pic", images[i]);
            listitems.add(listitem);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, listitems, R.layout.simple_adapter_test,
                new String[]{"animal_name", "animal_pic"}, new int[]{R.id.animal_name, R.id.animal_pic});
        ListView listView = findViewById(R.id.mylist);
        listView.setAdapter(simpleAdapter);

      	//点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //日志打印点击情况
                Log.i("-CRAZYIT-", names[position] + " clicked");
                Toast toast = Toast.makeText(MainActivity.this, names[position], Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
```

Toast显示信息，调用 makeText 函数：MainActivity.this为上下文对象(Context)；names[position]为当前对象，即被点击的对象，position为对象下标；Toast.LENGTH_SHORT是一个静态变量，显示间隔时长。

*除了Toast.LENGTH_SHORT，还有Toast.LENGTH_LONG。Toast是包含用户点击消息，每生成一个toast对象就会往显示队列里添加，根据设置的显示间隔时长，按点击顺序一次显示。由于时延，如果点击过快，会出现显示与点击不相符的情况。*



运行结果：

![image](https://github.com/vency799/experiment_03/blob/master/simadp_page.png)

![image](https://github.com/vency799/experiment_03/blob/master/simadp_page_02.png)

其他：

![image](https://github.com/vency799/experiment_03/blob/master/animal_pic.png)

![image](https://github.com/vency799/experiment_03/blob/master/colors.png)

![image](https://github.com/vency799/experiment_03/blob/master/Log.png)



2、创建自定义的AlertDialog

activity_main.xml添加一个按钮，用于触发自定义的AlertDialog对话框。

onClick属性设置对应的函数名称。

```java
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/btn_show"
        android:onClick="customView"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.498"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.093" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

新建一个login.xml用于设置对话框的布局。

```java
<?xml version="1.0" encoding="utf-8"?>
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent" android:layout_height="match_parent">

    <TableRow
        android:layout_width="match_parent"
        android:layout_height="56dp" >

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="334dp"
            android:layout_height="wrap_content"
            app:srcCompat="@drawable/androidapp" />
    </TableRow>

    <TableRow
        android:layout_width="match_parent"
        android:layout_height="44dp" >

        <EditText
            android:id="@+id/editTextTextPersonName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:ems="10"
            android:inputType="textPersonName"
            android:hint="@string/name"/>
    </TableRow>

    <TableRow
        android:layout_width="match_parent"
        android:layout_height="match_parent" >

        <EditText
            android:id="@+id/editTextTextPassword"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:ems="10"
            android:inputType="textPassword"
            android:hint="@string/pwd"/>
    </TableRow>
</TableLayout>
```

MainActivity.java设置AlertDialog组件。

使用getLayoutInflate()函数使布局立体化，导入上面建的login.xml文件，然后通过".setView()"将布局放入。

```java
package com.example.exp03_02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void customView(View source){
        TableLayout loginForm = (TableLayout)getLayoutInflater().inflate(R.layout.login, null);
        new AlertDialog.Builder(this)
                .setView(loginForm)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();
    }
}
```

运行结果：

![image](https://github.com/vency799/experiment_03/blob/master/res_exp03_02.png)



3、使用XML定义菜单

activity_main.xml添加一段文本，用于测试，id为"txt"。

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/txt"
        android:layout_width="match_parent"
        android:layout_height="59dp"
        android:text="@string/test_txt"
        android:textColor="#000000"
        android:textSize="18sp" />
</LinearLayout>
```

在MainActivity.java编辑菜单栏的内容

```java
package com.example.exp03_03;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //字体大小
    private static final int FONT_10 = 0x111;
    private static final int FONT_16 = 0x112;
    private static final int FONT_20 = 0x113;
    //"普通菜单栏"
    private static final int PLAIN_ITEM = 0x11b;
    //字体颜色
    private static final int FONT_RED = 0x116;
    private static final int FONT_BLACK = 0x117;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.txt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        SubMenu fontMenu = menu.addSubMenu("字体大小");
        //第一项菜单
        fontMenu.setHeaderTitle("选择字体大小");
        fontMenu.add(0, FONT_10, 0, "10号字体");
        fontMenu.add(0, FONT_16, 0, "16号字体");
        fontMenu.add(0, FONT_20, 0, "20号字体");
        //第二项菜单
        menu.add(0, PLAIN_ITEM, 0, "普通菜单项");
        //第三项菜单
        SubMenu colorMenu = menu.addSubMenu("字体颜色");
        colorMenu.setHeaderTitle("选择字体颜色");
        colorMenu.add(0, FONT_RED, 0, "红色");
        colorMenu.add(0, FONT_BLACK, 0, "黑色");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        switch (mi.getItemId()){
            case FONT_10: text.setTextSize(10*2); break;
            case FONT_16: text.setTextSize(16*2); break;
            case FONT_20: text.setTextSize(20*2); break;
            case FONT_RED: text.setTextColor(Color.RED); break;
            case FONT_BLACK: text.setTextColor(Color.BLACK); break;
            case PLAIN_ITEM:
                Toast.makeText(MainActivity.this, "您单击了普通菜单项", Toast.LENGTH_SHORT)
                        .show();
                break;
        }
        return true;
    }
}
```

运行截图：

![image](https://github.com/vency799/experiment_03/blob/master/res_exp03_03.png)

![image](https://github.com/vency799/experiment_03/blob/master/res_exp03_03_2.png)

![image](https://github.com/vency799/experiment_03/blob/master/res_exp03_03_3.png)

![image](https://github.com/vency799/experiment_03/blob/master/res_exp03_03_4.png)



4、创建上下文操作模式(ActionMode)的上下文菜单

simpleadapter.xml设置布局，点击函数为"click_select"

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:onClick="click_select">

    <ImageView
        android:id="@+id/image_my_android"
        android:layout_width="109dp"
        android:layout_height="80dp"
        android:layout_weight="1"
        app:srcCompat="@drawable/my_android" />

    <TextView
        android:id="@+id/text_item"
        android:layout_width="268dp"
        android:layout_height="80dp"
        android:layout_weight="1"
        android:gravity="center|left"
        android:textSize="24sp" />


</LinearLayout>
```

![image](https://github.com/vency799/experiment_03/blob/master/am_simadp_view.png)

activity_main.xml设置ListView，id为"my_list"

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">


    <ListView
        android:id="@+id/my_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```

MainActivity设置布局与监听事件

①simpleadapter

```java
public class MainActivity extends AppCompatActivity {

    public String[] names = new String[] {"One", "Two", "Three", "Four", "Five", "Six"};
    public int[] images = new int[] {R.drawable.my_android};
    public int selected_items = 0;
    public ActionMode am;
    public HashMap<View, Boolean> vis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vis = new HashMap<>();

        List<Map<String, Object>> items = new ArrayList<>();
        for(int i=0; i < names.length; i++){
            Map<String, Object> item = new HashMap<>();
            item.put("image_android", images[0]);
            item.put("text_item", names[i]);
            items.add(item);
        }

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, items, R.layout.simadapter,
                new String[]{"image_android", "text_item"}, new int[]{R.id.image_my_android, R.id.text_item});
        ListView listView = findViewById(R.id.my_list);

        listView.setAdapter(simpleAdapter);

    }
```

②ActionMode

```java
//创建ActionMode
    ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_context, menu);
            return true;
        }
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            mode.setTitle(selected_items+"selected");
            return true;
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };

    public void click_select(View V){
        if(am == null){
            am = startActionMode(callback);
        }
        LinearLayout linearLayout = (LinearLayout) V;
        if( vis.get(V) == null || !vis.get(V)){
            linearLayout.setBackgroundColor(Color.GRAY);
            vis.put(V, true);
            selected_items++;
        }else{
            linearLayout.setBackgroundColor(Color.WHITE);
            vis.put(V, false);
            selected_items--;
        }
        callback.onActionItemClicked(am, null);
    }
```

运行结果：

![image](https://github.com/vency799/experiment_03/blob/master/am_running_view.png)

![image](https://github.com/vency799/experiment_03/blob/master/am_selected_view.png)
