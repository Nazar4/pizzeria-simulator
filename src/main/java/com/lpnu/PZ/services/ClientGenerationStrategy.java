package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Client;

import java.util.List;

public abstract class ClientGenerationStrategy {
    public abstract List<Client> generateClients();
}
