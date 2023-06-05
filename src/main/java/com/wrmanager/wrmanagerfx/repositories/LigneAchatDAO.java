package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.LigneAchat;
import com.wrmanager.wrmanagerfx.entities.Vendeur;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LigneAchatDAO extends DAORepository<LigneAchat,Long>{

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
            Optional.ofNullable(em.find(LigneAchat.class,id)).ifPresent(LigneAchat -> em.remove(LigneAchat));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }


    @Override
    public Optional<LigneAchat> getById(Long id) {
        return Optional.ofNullable(em.find(LigneAchat.class,id));
    }

    @Override
    public Set<LigneAchat> getAll() {
        List<LigneAchat> LigneAchats = em.createQuery("SELECT c FROM LigneAchat c").getResultList();
        return LigneAchats.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM LigneAchat").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }




}
