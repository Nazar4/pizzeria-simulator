package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Client;
import com.lpnu.PZ.utils.GlobalConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RandomGenerationStrategy extends ClientGenerationStrategy {

    public RandomGenerationStrategy(final int numberOfPizzasInMenu, final int minimumTimeToCreatePizza) {
        super(numberOfPizzasInMenu, minimumTimeToCreatePizza);
    }

    @Override
    public void generateClientWithInterval() {
        getClientGeneratorThreadPool().submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    final Client client = generateClient();
                    addToAppropriateQueue(client);

                    final int sleepTime = ThreadLocalRandom.current().nextInt(GlobalConstants.RANDOM_BOTTOM_BOUND_TO_GENERATE_CLIENT,
                            GlobalConstants.RANDOM_UPPER_BOUND_TO_GENERATE_CLIENT);
                    TimeUnit.SECONDS.sleep(sleepTime);
                } catch (final InterruptedException e) {
                    log.error("Error in client generation thread: {}", e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
}