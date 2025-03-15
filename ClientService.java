package com.example.mynewfishshop.services;

import com.example.mynewfishshop.models.ClientModel;
import com.example.mynewfishshop.repos.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepo clientRepo;

    public List<ClientModel> findClientModelsByActual(boolean actual){
        return clientRepo.findClientModelsByActual(actual);
    }
}
