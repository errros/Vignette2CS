package com.wrmanager.wrmanagerfx.services;

import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.repositories.FournisseurDAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.wrmanager.wrmanagerfx.Constants.*;

public class FournisseurService {



    public Fournisseur save(Fournisseur fournisseur){
        
        var ret =  fournisseurDAO.save(fournisseur);

        fournisseursList.add(0,ret);

        return ret;



    }

    public Fournisseur update( Fournisseur fournisseur ){

        var ret =      fournisseurDAO.update(fournisseur);

        fournisseursViewController.getFournisseursTable().refresh();
       return ret;


    }

    public void delete(Fournisseur fournisseur){
     fournisseurDAO.deleteById(fournisseur.getId());
     fournisseursList.remove(fournisseur);
     sortRowsByLastTimeAdded();

    }

    public void  sortRowsByLastTimeAdded(){

        fournisseursList.sort(Comparator.comparing(Fournisseur::getCreeLe).reversed());

    }


    public List<Fournisseur> getAll(){

        var ret = fournisseurDAO.getAll().stream().toList();

   return ret;
    }




}
