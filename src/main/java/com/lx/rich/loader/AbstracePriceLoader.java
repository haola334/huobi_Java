package com.lx.rich.loader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstracePriceLoader implements PriceLoader {

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void startLoad() {
        executorService.scheduleAtFixedRate(() -> {

            load();

        }, 30, 10, TimeUnit.MILLISECONDS);
    }

    protected abstract void load();

}
