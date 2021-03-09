package com.example.themoviedb.ui.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Dialogs {

    public void showAlertDialog(String message, Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Atención");
        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
