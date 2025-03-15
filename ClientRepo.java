package com.example.mynewfishshop.repos;


import com.example.mynewfishshop.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<ClientModel,Long> {
    List<ClientModel> findClientModelsByActual(boolean actual);
}
