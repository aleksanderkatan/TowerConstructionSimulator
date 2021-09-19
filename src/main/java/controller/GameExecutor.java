package controller;

public interface GameExecutor extends Runnable {
    void run();
    void step();
}
