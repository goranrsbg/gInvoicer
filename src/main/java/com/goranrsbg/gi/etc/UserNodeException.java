package com.goranrsbg.gi.etc;

import javafx.scene.Node;

/**
 *
 * @author goranrsbg
 */
public class UserNodeException extends Exception {

    private final Node node;

    public UserNodeException(String message, Node node) {
        super(message);
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

}
