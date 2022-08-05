package com.shamrock.reework.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.widget.ProgressBar;

public class ProgressOverlay {
    /**
     * The color of the circular spinner on API 21+ (null to use the default one)
     */
    public static Integer MATERIAL_COLOR = Color.WHITE;
    static  ProgressDialog dialog;

    public ProgressOverlay(final Context context, final boolean cancelable, final DialogInterface.OnCancelListener cancelListener) {
        dialog  = ProgressDialog.show(context, null, null, true, cancelable, cancelListener);
    }

    /**
     * Creates and shows a progress dialog with no borders, frame nor text over the default semi-transparent dialogs' background.
     *
     * @return ProgressDialog The new dialog instance
     */
    public static ProgressDialog show(final Context context) {
        return show(context, false);
    }

    /**
     * Creates and shows a progress dialog with no borders, frame nor text over the default semi-transparent dialogs' background.
     *
     * @return ProgressDialog The new dialog instance
     */
    public static ProgressDialog show(final Context context, final boolean cancelable) {
        return show(context, cancelable, null);
    }

    /**
     * Creates and shows a progress dialog with no borders, frame nor text over the default semi-transparent dialogs' background.
     *
     * @return ProgressDialog The new dialog instance
     */
    @SuppressLint("NewApi")
    public static ProgressDialog show(final Context context, final boolean cancelable, final DialogInterface.OnCancelListener cancelListener) {
//        final ProgressDialog dialog = ProgressDialog.show(context, null, null, true, cancelable, cancelListener);
        final ProgressBar view = new ProgressBar(context);
        if (MATERIAL_COLOR != null && Build.VERSION.SDK_INT >= 21)
            view.setIndeterminateTintList(ColorStateList.valueOf(MATERIAL_COLOR));
        dialog.setContentView(view);
        //noinspection ConstantConditions
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }
}
