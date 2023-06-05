package com.wrmanager.wrmanagerfx.services;

import com.wrmanager.wrmanagerfx.entities.Achat;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.entities.Vente;

import java.util.Comparator;
import java.util.List;

import static com.wrmanager.wrmanagerfx.Constants.*;

public class VenteService {
    public List<Vente> getAll() {
        return venteDAO.getAll().stream().toList();
    }
    public void sortRowsByLastTimeAdded(){

        ventesList.sort(Comparator.comparing(Vente::getCreeLe).reversed());

    }


}
