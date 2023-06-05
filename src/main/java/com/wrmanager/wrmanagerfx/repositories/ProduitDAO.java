package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.Produit;
import com.wrmanager.wrmanagerfx.entities.Produit;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static  com.wrmanager.wrmanagerfx.Constants.*;

public class ProduitDAO extends DAORepository<Produit,Long>{
    static {
        try {

            em = Persistence.createEntityManagerFactory("that-unit").createEntityManager();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void deleteById(Long id) {
        try {
            em.getTransaction().begin();
            Optional.ofNullable(em.find(Produit.class,id)).ifPresent(Produit -> {
                        em.remove(Produit);
                    }
            );
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }


    @Override
    public Optional<Produit> getById(Long id) {
        return Optional.ofNullable(em.find(Produit.class,id));
    }

    @Override
    public Set<Produit> getAll() {
        List<Produit> Produits = em.createQuery("SELECT c FROM Produit c").getResultList();
        return Produits.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Produit").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }



}
