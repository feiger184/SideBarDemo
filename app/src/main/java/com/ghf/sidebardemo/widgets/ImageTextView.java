package com.ghf.sidebardemo.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ghf.sidebardemo.utils.BitmapUtil;

/**
 * @Description: 文字图片，这个相信大家都知道，比如QQ底部导航上的未读消息数
 *
 */ 
public class ImageTextView extends TextView {

	public ImageTextView(Context context) {
		super(context);
	}

	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setIconText(Context context, String text) {
		String s = this.getText().toString().substring(0, 1);
		Bitmap bitmap = BitmapUtil.getIndustry(context, s);
		Drawable drawable = BitmapUtil.bitmapTodrawable(bitmap);
		this.setCompoundDrawables(drawable, null, null, null);
	}
}
