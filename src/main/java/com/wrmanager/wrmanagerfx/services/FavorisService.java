package com.wrmanager.wrmanagerfx.services;

import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.entities.GroupeFavoris;
import com.wrmanager.wrmanagerfx.entities.ProduitFavori;

import static com.wrmanager.wrmanagerfx.Constants.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FavorisService {




    public void sauvegarderUpdateGroupeFavoris(List<GroupeFavoris> groupeFavorisesTmp ) {
        //delete groupefavoris


   var filteredList = groupeFavorisesList.stream().filter(groupeFavoris -> !groupeFavorisesTmp.contains(groupeFavoris)).collect(Collectors.toList());
   filteredList.forEach(groupeFavoris -> delete(groupeFavoris));



        groupeFavorisesTmp.forEach(groupeFavoris -> groupeFavorisDAO.update(groupeFavoris));


        groupeFavorisesTmp.stream().filter(groupeFavoris -> !groupeFavorisesList.contains(groupeFavoris)).
                forEach(groupeFavoris -> groupeFavorisesList.add(groupeFavoris));

        sortRowsByLastTimeAdded();
        /*
        for(int i=0;i<groupeFavorisesList.size() ; i++){
            for(int j=0;j<groupeFavorisesList.get(i).getProduitFavoris().size();j++){
                produitFavoriDAO.deleteById(groupeFavorisesList.get(i).getProduitFavoris().get(j).getId());
            }

        }*/






    }


    private void save(GroupeFavoris gf){
        var result = groupeFavorisDAO.save(gf);
        groupeFavorisesList.add(result);

    }



    public void delete (GroupeFavoris gf){

        groupeFavorisDAO.deleteById(gf.getId());
        groupeFavorisesList.remove(gf);

    }

    private void deleteProduitFavoriFromGroupeFavori(GroupeFavoris gf ,ProduitFavori pf){
        produitFavoriDAO.deleteById(pf.getId());
        gf.removeProduitFavori(pf);
    }
    private void addProduitFavoriToGroupeFavori(GroupeFavoris gf ,ProduitFavori pf){
        gf.addProduitFavori(pf);
        produitFavoriDAO.save(pf);
    }

    public void sortRowsByLastTimeAdded(){

        groupeFavorisesList.sort(Comparator.comparing(GroupeFavoris::getCreeLe).reversed());

    }


    public List<GroupeFavoris> getAll(){

        return groupeFavorisDAO.getAll().stream().toList();
    }



}
