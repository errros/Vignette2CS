package com.wrmanager.wrmanagerfx.repositories;

import com.wrmanager.wrmanagerfx.entities.LigneVente;
import com.wrmanager.wrmanagerfx.entities.Notification;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class NotificationDAO extends DAORepository<Notification,Long>{

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
            Optional.ofNullable(em.find(Notification.class,id)).ifPresent(notif -> em.remove(notif));
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }



    }

    @Override
    public Optional<Notification> getById(Long id) {
        return Optional.ofNullable(em.find(Notification.class,id));
    }

    @Override
    public Set<Notification> getAll() {
        List<Notification> notifs = em.createQuery("SELECT c FROM Notification c").getResultList();
        return notifs.stream().collect(Collectors.toSet());

    }

    @Override
    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Notification").executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }



    }



}
