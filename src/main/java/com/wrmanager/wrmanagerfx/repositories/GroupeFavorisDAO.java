package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.GroupeFavoris;
import com.wrmanager.wrmanagerfx.entities.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.wrmanager.wrmanagerfx.Constants.*;

import javax.persistence.Persistence;

public class GroupeFavorisDAO extends DAORepository<GroupeFavoris,Long>{

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
            Optional.ofNullable(em.find(GroupeFavoris.class,id)).ifPresent(groupeFavoris -> em.remove(groupeFavoris));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }

    }

    @Override
    public Optional<GroupeFavoris> getById(Long id) {

        return Optional.ofNullable(em.find(GroupeFavoris.class,id));

    }

    @Override
    public Set<GroupeFavoris> getAll() {

        List<GroupeFavoris> groupeFavorises = new ArrayList<>();

//        ProduitFavoriDAO produitFavoriDAO = new ProduitFavoriDAO();
        groupeFavorises.addAll(em.createQuery("SELECT c FROM GroupeFavoris c ").getResultList());
  /*      groupeFavorises.forEach(groupeFavoris -> {

            groupeFavoris.getProduitFavoris().clear();
                 groupeFavoris.getProduitFavoris().addAll(produitFavoriDAO.getAll().stream().filter(produitFavori ->
                         produitFavori.getGroupeFavoris().equals(groupeFavoris)).collect(Collectors.toList()));
        }
        );
*/


        return groupeFavorises.stream().collect(Collectors.toSet());

    }
   /* public Set<GroupeFavoris> getAllForSave() {

        List<GroupeFavoris> groupeFavorises = new ArrayList<>();

        groupeFavorises.addAll(em.createQuery("SELECT c FROM GroupeFavoris c").getResultList());


        return groupeFavorises.stream().collect(Collectors.toSet());


    }
*/
    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM GroupeFavoris").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }



    }

