package com.plugtree.spotplug.client.util;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;

public abstract class GenericAsyncCallback<T> implements AsyncCallback<T> {

    @Override
    public void onFailure(Throwable throwable) {

        DialogBox dialogBox = new DialogBox(true);
        dialogBox.add(new Label("Advertencia, problema en la conexion al servidor."));
        dialogBox.center();
        dialogBox.show();
    }
}
