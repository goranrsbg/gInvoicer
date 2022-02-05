package com.goranrsbg.gi.etc;

import javafx.scene.Parent;

/**
 * Contains Controller and Node instance.
 *
 * @author Goran Cvijanovic
 * @param <T> Controller class type.
 */
public class View<T> {

    private final T controller;
    private final Parent node;

    public View(Parent node, T controller) {
        this.controller = controller;
        this.node = node;
    }

    public T getController() {
        return controller;
    }

    public Parent getNode() {
        return node;
    }

}
