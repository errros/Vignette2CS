package com.wrmanager.wrmanagerfx.repositories;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Optional;
import java.util.Set;


 abstract class DAORepository<T,E> {

     public static EntityManager em;
     static {
    try {

        em = Persistence.createEntityManagerFactory("that-unit").createEntityManager();

    } catch (Exception e){
        e.printStackTrace();
    }

    }


    //save a new instance of an entity returned obj will containt the attributed id by the db

    public  T save(T obj) {

        try {

            em.getTransaction().begin();

            em.persist(obj);
            em.flush();
            em.refresh(obj);
            em.getTransaction().commit();


        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        return obj;


    }

public abstract void deleteById(E id);



     //update an existing intance of entity returned object is the same
     //if the instance isnt saved in the db that will throw an error
     public T update(T obj){

    try {

        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();

    } catch (Exception e){
        em.getTransaction().rollback();
        e.printStackTrace();
    }

    return obj;


}

public abstract Optional<T> getById(E id);

public abstract  Set<T> getAll();
     public abstract void deleteAll();

}