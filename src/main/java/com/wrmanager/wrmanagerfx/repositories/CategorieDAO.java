package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.entities.Categorie;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CategorieDAO extends DAORepository<Categorie , Long>{


    @Override
    public void deleteById(Long id) {
        try {
            em.getTransaction().begin();
            Optional.ofNullable(em.find(Categorie.class,id)).ifPresent(categorie -> em.remove(categorie));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }


    @Override
    public Optional<Categorie> getById(Long id) {
        return Optional.ofNullable(em.find(Categorie.class,id));
    }

    @Override
    public Set<Categorie> getAll() {
        List<Categorie> categories = em.createQuery("SELECT c FROM Categorie c").getResultList();
        return categories.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Categorie").executeUpdate();

         em.getTransaction().commit();

         save(Constants.DEFAULT_CATEGORIE_PRODUIT);

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }
}
