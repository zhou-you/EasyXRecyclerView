package com.zhouyou.recyclerview.divider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;

import com.zhouyou.recyclerview.adapter.MenuAdapter;

/**
 * <p>描述：解决复杂的分割线</p>
 * PaintProvider、SizeProvider、ColorProvider、VisibilityProvider、MarginProvider根据需要可以自己定制，
 * 并不是全部都必须实现，根据需求选择对应的Provider
 * 作者： zhouyou<br>
 * 日期： 2017/12/22 10:33 <br>
 * 版本： v1.0<br>
 */
public class ComplexAdapter extends MenuAdapter implements
        com.zhouyou.recyclerview.divider.FlexibleDividerDecoration.PaintProvider,
        //FlexibleDividerDecoration.SizeProvider,
        //FlexibleDividerDecoration.ColorProvider,
        com.zhouyou.recyclerview.divider.FlexibleDividerDecoration.VisibilityProvider,
        HorizontalDividerItemDecoration.MarginProvider {
    public ComplexAdapter(Context context) {
        super(context);
    }

    @Override
    public int dividerLeftMargin(int position, RecyclerView parent) {
        if (position < 10) {
            return position * 20 + 20;
        } else {
            return (20 - position) * 20 + 20;
        }
    }

    @Override
    public int dividerRightMargin(int position, RecyclerView parent) {
        if (position < 10) {
            return position * 20;
        } else {
            return (20 - position) * 20;
        }
    }

    @Override
    public boolean shouldHideDivider(int position, RecyclerView parent) {
        if (position == 14 || position == 15) {
            return true;
        }
        return false;
    }

    @Override
    public Paint dividerPaint(int position, RecyclerView parent) {
        Paint paint = new Paint();
        switch (position % 10) {
            case 0:
                paint.setColor(Color.RED);
                paint.setStrokeWidth(30);
                break;
            case 1:
                paint.setColor(Color.MAGENTA);
                paint.setStrokeWidth(10);
                break;
            default:
                if (position % 2 == 0) {
                    paint.setColor(Color.BLUE);
                    paint.setAntiAlias(true);
                    paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
                } else {
                    paint.setColor(Color.GREEN);

                }
                paint.setStrokeWidth(2 + position);
                break;
        }
        return paint;
    }

    //    @Override
//    public int dividerColor(int position, RecyclerView parent) {
//        switch (position % 10) {
//            case 0:
//                return Color.RED;
//            case 1:
//                return Color.MAGENTA;
//            default:
//                if (position % 2 == 0) {
//                    return Color.BLUE;
//                } else {
//                    return Color.GREEN;
//                }
//        }
//    }
//
//    @Override
//    public int dividerSize(int position, RecyclerView parent) {
//        switch (position % 10) {
//            case 0:
//                return 30;
//            case 1:
//                return 10;
//            default:
//                return 2+position;
//        }
//    }
}
