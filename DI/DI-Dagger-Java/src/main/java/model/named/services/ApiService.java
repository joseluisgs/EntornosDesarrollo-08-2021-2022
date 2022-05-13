package model.named.services;

import model.named.IApiService;

import java.util.UUID;

abstract class ApiService implements IApiService {
    private final String id = UUID.randomUUID().toString();

    @Override
    public String toString() {
        return "ApiService{" +
                "id='" + id + '\'' +
                '}';
    }
}
