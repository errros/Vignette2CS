package com.wrmanager.wrmanagerfx.services;

import com.wrmanager.wrmanagerfx.entities.Commande;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.entities.LigneCommande;
import com.wrmanager.wrmanagerfx.entities.Vendeur;

import javax.swing.text.html.Option;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.wrmanager.wrmanagerfx.Constants.*;


public class CommandeService {



    public Commande save(Commande commande , List<LigneCommande> ligneCommandes){
/*
         commande.addVendeur(vendeur);
         ligneCommandes.stream().forEach(ligneCommande -> commande.addLigneCommande(ligneCommande));

        var ret = commandeDAO.save(commande);
        commandesList.add(0,ret);
        return ret;*/
    return null;
    }


    public Commande update( Commande commande , List<LigneCommande> ligneCommandes) {

        ligneCommandes.forEach(ligneCommande -> {
                    if (!commande.getLignesCommande().contains(ligneCommande)){

                    }

                }
            );

        var c = commandeDAO.update(commande);
        return c;


    }

    public void delete(Commande commande){


        Optional.ofNullable(commande.getVendeur()).ifPresent(vendeur -> commande.removeVendeur(vendeur));
        commandeDAO.deleteById(commande.getId());
        commandesList.remove(commande);
        sortRowsByLastTimeAdded();


    }


    public void sortRowsByLastTimeAdded(){

        commandesList.sort(Comparator.comparing(Commande::getCreeLe).reversed());

    }

    public List<Commande> getAll(){

        return commandeDAO.getAll().stream().toList();
    }



}
