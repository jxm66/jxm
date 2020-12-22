package com.example.a09;


import android.content.Context;



public class utils2 {
	private static float mDensity = 160.0F;

	public static int dip2px(float paramFloat) {
		return (int) (0.5F + paramFloat * mDensity);
	}


	public static int px2dip(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat / mDensity);
	}

	public static int px2sp(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				/ paramContext.getResources().getDisplayMetrics().scaledDensity);
	}

	public static int sp2px(Context paramContext, float paramFloat) {
		return (int) (paramContext.getResources().getDisplayMetrics().scaledDensity * (paramFloat - 0.5F));
	}
}
