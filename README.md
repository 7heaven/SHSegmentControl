[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SHSegmentControl-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1770) [![jitpack](https://img.shields.io/github/tag/7heaven/SHSegmentControl.svg?label=JitPack%20Maven)](https://img.shields.io/github/release/7heaven/SHSegmentControl.svg?label=JitPack%20Maven) [![Build Status](http://img.shields.io/travis/7heaven/SHSegmentControl.svg)](https://travis-ci.org/7heaven/SHSegmentControl)
[![License](https://img.shields.io/badge/apache-2.0-orange.svg)](LICENSE)
[ ![Download](https://api.bintray.com/packages/7heaven/maven/SHSegmentControl/images/download.svg) ](https://bintray.com/7heaven/maven/SHSegmentControl/_latestVersion)

#a simple SegmentControl Widget

![art2](arts/arts2.gif)

![art1](arts/arts1.gif)

## Usage：

### add dependency to build.gradle：


```groovy
dependencies {
    compile 'com.7heaven.widgets:segmentcontrol:1.16'
}
```

set segmentControl's property using attrs,using '|' to separate segments.

* **selectedColor** attribute for setting the selected color
* **normalColor** attribute for setting the unselected color
* **textColors** attribute for setting text colors, this attribute accept ColorStateList so you can set text selected color & text unselected color using this attribute,once this attribute is set, the previously selectedColor & normalColor attributes will not affect the text color
* **backgroundColors** for setting the round corner background stroke color, separators color & selected background color,same as textColors, when this attribute is set, previously selectedColor & normalColor attributes will not affect the background color, this attribute accept ColorStateList
* **cornerRadius** setting the corner radius of the background
* **boundWidth** setting the round corner background stroke width
* **separatorWidth** setting width of separators
* **texts** setting the string contents, separated by '|'
* **verticalGap** vertical padding
* **horizonGap** horizontal padding

**noticed that textColors & backgroundColors has higher priority than selectedColor & normalColor**

``` xml
<com.sevenheaven.segmentcontrol.SegmentControl
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/segment_control"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="20dp"                       	
    android:textSize="18sp"
	app:block_direction="horizontal"
    app:selectedColor="#32ADFF"
	app:normalColor="#FFFFFF"
    app:textColors="@color/text_colors"
    app:backgroundColors="@color/background_color"
    app:cornerRadius="5dp"
    app:separatorWidth="2dp"
    app:boundWidth="4dp"
    app:texts="啊啊|啦啦啦|哈哈哈|顶顶顶顶"
    app:verticalGap="10dp"
    app:horizonGap="10dp"/>
```

using OnSegmentControlClickListener to listen to segment change event.

```java
mSegmentHorzontal = (SegmentControl) findViewById(R.id.segment_control);
mSegmentHorzontal.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
    @Override
    public void onSegmentControlClick(int index) {
        Log.i(TAG, "onSegmentControlClick: index = " + index);
    }
});
```
