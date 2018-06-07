package com.virtuslab.rx_intro.task;

public class Pair<P, S> {
    public final P predecessor;
    public final S successor;

    public Pair(P predecessor, S successor) {
        this.predecessor = predecessor;
        this.successor = successor;
    }
}
