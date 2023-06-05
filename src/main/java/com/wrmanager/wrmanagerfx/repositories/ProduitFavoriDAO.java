package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.ProduitFavori;
import com.wrmanager.wrmanagerfx.entities.ProduitFavori;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProduitFavoriDAO extends DAORepository<ProduitFavori,Long>{

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
            Optional.ofNullable(em.find(ProduitFavori.class,id)).ifPresent(produitFavori -> em.remove(produitFavori));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }

    }

    @Override
    public Optional<ProduitFavori> getById(Long id) {

        return Optional.ofNullable(em.find(ProduitFavori.class,id));

    }

    @Override
    public Set<ProduitFavori> getAll() {


        List<ProduitFavori> produitFavoris = em.createQuery("SELECT c FROM ProduitFavori c").getResultList();
        return produitFavoris.stream().collect(Collectors.toSet());


    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM ProduitFavori").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }






}
