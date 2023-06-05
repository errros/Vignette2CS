package com.wrmanager.wrmanagerfx.services;

import com.wrmanager.wrmanagerfx.entities.Categorie;
import com.wrmanager.wrmanagerfx.entities.Produit;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.wrmanager.wrmanagerfx.Constants.*;


//No duplicate name for categorie
public class CategorieService {



    public Categorie save (Categorie categorie){

        var ret = categorieDAO.save(categorie);
         categoriesList.add(0,ret);

         return ret;

    }
    public Categorie update (Categorie categorie){

        return categorieDAO.update(categorie);
    }
    public void delete (Categorie categorie){
        categorie.getProduits().stream().forEach(produit -> produit.removeCategorie(categorie));
        categorieDAO.deleteById(categorie.getId());
         sortRowsByLastTimeAdded();
    }

    public Set<Categorie> getAll(){

        var ret = categorieDAO.getAll();
        return ret;
    }

    public void sortRowsByLastTimeAdded(){

        categoriesList.sort(Comparator.comparing(Categorie::getCreeLe).reversed());

    }





}
