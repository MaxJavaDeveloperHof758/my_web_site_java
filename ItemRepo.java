package com.example.mynewfishshop.repos;

import com.example.mynewfishshop.models.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<ItemModel,Long> {
}
