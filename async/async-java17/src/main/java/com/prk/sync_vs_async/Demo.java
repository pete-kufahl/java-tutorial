package com.prk.sync_vs_async;



public class Demo {

    public static void main(String[] args) {
        double sync = SynchronousTasks.run(10);
        double conc = ConcurrentTasks.run(10);
        double async = AsyncTasks.run(10);
        System.out.println("average SYNC runtime = " + sync);
        System.out.println("average CONC runtime = " + conc);
        System.out.println("average ASYNC runtime = " + async);
    }
}
