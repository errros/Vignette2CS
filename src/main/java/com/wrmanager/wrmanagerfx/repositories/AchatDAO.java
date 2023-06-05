package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.Achat;
import com.wrmanager.wrmanagerfx.entities.Achat;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AchatDAO  extends DAORepository<Achat, Long>{



    @Override
    public void deleteById(Long id) {
        try {
            em.getTransaction().begin();

            Optional.ofNullable(em.find(Achat.class,id)).ifPresent(Achat -> {

                        em.remove(Achat);

                    }
            );

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }


    }


    @Override
    public Optional<Achat> getById(Long id) {
        return Optional.ofNullable(em.find(Achat.class,id));
    }

    @Override
    public Set<Achat> getAll() {
        List<Achat> Achats = em.createQuery("SELECT c FROM Achat c").getResultList();
        return Achats.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Achat").executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }



}
