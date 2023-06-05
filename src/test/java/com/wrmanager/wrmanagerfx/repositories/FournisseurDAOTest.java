package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.wrmanager.wrmanagerfx.Constants.*;

class FournisseurDAOTest {

    @Test
    void getAll() {
        Fournisseur f = fournisseurDAO.getById(14l).get();
        f.setNom("bedeltah");
        fournisseurDAO.update(f);



    }
}