package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Client;
import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.utils.GlobalConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class IntervalGenerationStrategy extends ClientGenerationStrategy {

    public IntervalGenerationStrategy(final int numberOfPizzasInMenu, final int minimumTimeToCreatePizza) {
        super(numberOfPizzasInMenu, minimumTimeToCreatePizza);
    }

    @Override
    public void generateClientWithInterval() {
        getClientGeneratorThreadPool().submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    final Client client = generateClient();
                    addToAppropriateQueue(client);

                    TimeUnit.SECONDS.sleep(GlobalConstants.INTERVAL_TO_GENERATE_CLIENT);
                } catch (InterruptedException e) {
                    log.error("Error in client generation thread: {}", e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
}
