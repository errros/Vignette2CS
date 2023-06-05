package com.wrmanager.wrmanagerfx.services;

import com.wrmanager.wrmanagerfx.controllers.AchatsViewController;
import com.wrmanager.wrmanagerfx.controllers.StockViewController;
import com.wrmanager.wrmanagerfx.entities.Achat;
import com.wrmanager.wrmanagerfx.entities.LigneAchat;
import com.wrmanager.wrmanagerfx.entities.Produit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.wrmanager.wrmanagerfx.Constants.*;
public class AchatService {

    public Achat save(Achat achat){
        return achatDao.save(achat);
    }


     public void sortRowsByLastTimeAdded(){

        achatsList.sort(Comparator.comparing(Achat::getCreeLe).reversed());

    }


    private void setProduitUptoDateWithLastLigneAchat(LigneAchat ligneAchat){

        Produit p = ligneAchat.getProduit();
        p.setDatePeremption(ligneAchat.getDatePeremption());

        var pp = produitDAO.update(p);






    }

    private void removeLigneAchatUpdateQtyTotale(LigneAchat ligneAchat){

        Produit p = ligneAchat.getProduit();

        var result =  produitDAO.update(p);
/*
        IntStream.range(0,StockViewController.produits.size())
                .filter(i->StockViewController.produits.get(i).equals(result.getId())).findAny().ifPresent(index->{
                    System.out.println(index);
                    StockViewController.produits.remove(index);
                    StockViewController.produits.add(index, result);
                });*/
    }

    public Achat update(Achat achat , List<LigneAchat> ligneAchats){

        achat.getLigneAchats().forEach(this::removeLigneAchatUpdateQtyTotale);

        achat.setLigneAchats(ligneAchats);

        var ret = achatDao.update(achat);

        if(!achatsList.contains(ret)){
        achat.getLigneAchats().forEach(this::setProduitUptoDateWithLastLigneAchat);
            achatsList.add(0, ret);
        }


        achatsViewController.getAchatsTable().refresh();
        stockViewController.getStockTable().refresh();
        produitsViewController.getProduitsTable().refresh();
    return ret;
    }

    public void delete(Achat achat) {

        achat.getLigneAchats().forEach(this::removeLigneAchatUpdateQtyTotale);

        achatDao.deleteById(achat.getId());
        achatsList.remove(achat);

      achatsViewController.getAchatsTable().refresh();
      stockViewController.getStockTable().refresh();

    }

    public Optional<Achat> getOne(Achat achat){
        return achatDao.getById(achat.getId());
    }
    public List<Achat> getAll() {
    return achatDao.getAll().stream().toList();
    }
}
