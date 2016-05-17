[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SHSegmentControl-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1770) [![jitpack](https://img.shields.io/github/tag/7heaven/SHSegmentControl.svg?label=JitPack%20Maven)](https://img.shields.io/github/release/7heaven/SHSegmentControl.svg?label=JitPack%20Maven) [![Build Status](http://img.shields.io/travis/7heaven/SHSegmentControl.svg)](https://travis-ci.org/7heaven/SHSegmentControl)
[![License](http://img.shields.io/:license-mit-blue.svg)](LICENSE)

#a simple SegmentControl Widget

![art2](arts/arts2.gif)

![art1](arts/arts1.gif)

##Usage
***

Step 1. Add it in your root build.gradle at the end of repositories:
```java
    allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
```

Step 2. Add the dependency
```java
    dependencies {
		compile 'com.github.User:Repo:Tag'
	}
```

Step 3. use it!
set segmentControl's property using attrs,using '|' to separate segments.

``` xml
<com.sevenheaven.segmentcontrol.SegmentControl
    android:id="@+id/segment_control"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="20dp"
    android:textSize="18sp"
    app:colors="#0099CC"
    app:cornerRadius="5dp"
    app:direction="vertical"
    app:horizonGap="10dp"
    app:textSelectedColors="#E74C3C"
    app:texts="啊啊|啦啦啦|哈哈哈|顶顶顶顶"
    app:verticalGap="10dp"/>
```

using OnSegmentControlClickListener to listen to segment change event.

```xml
mSegmentHorzontal = (SegmentControl) findViewById(R.id.segment_control);
mSegmentHorzontal.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
    @Override
    public void onSegmentControlClick(int index) {
        Log.i(TAG, "onSegmentControlClick: index = " + index);
    }
});
```