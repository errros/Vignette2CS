package com.wrmanager.wrmanagerfx.services;

import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.entities.LigneAchat;
import com.wrmanager.wrmanagerfx.entities.Notification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.wrmanager.wrmanagerfx.Constants.*;
public class NotificationService {

    public Notification save(Notification notification , LigneAchat ligneAchat){

        notification.setLigneAchat(ligneAchat);
        return notificationDAO.save(notification);
    }

    public List<Notification> saveTodayNotifs() {
        LocalDate today = LocalDate.now();
        var notifs = notificationService.getAll();

        List<Notification> ret = new ArrayList<>();

        var lignesAchat = ligneAchatDAO.getAll().stream().filter(l-> Optional.ofNullable(l.getProduit()).isPresent()).collect(Collectors.toList());

        //by date peremption
   lignesAchat.stream().filter(ligneAchat -> Optional.ofNullable(ligneAchat.getDatePeremption()).isPresent()).filter(ligneAchat -> ligneAchat.getDatePeremption().equals(
                        Date.valueOf(today.plusDays(ligneAchat.getProduit().getJoursAlerte()))))
                .filter(ligneAchat -> ligneAchat.getProduit().getQtyTotale()>0).forEach(ligneAchat -> {
                    var notif =  Notification.builder().contenu(ligneAchat.getProduit().getDesignation()).build();

                    if(notifs.stream().filter(n->n.getLigneAchat().equals(ligneAchat)).findAny().isEmpty()) {
                        ret.add(notificationService.save(notif, ligneAchat));
                    }});

// by qtyAlerte
        lignesAchat.stream().filter(ligneAchat -> ligneAchat.getProduit().getQtyTotale()<= ligneAchat.getProduit().getQtyAlerte()).forEach(ligneAchat -> {
            var notif =  Notification.builder().contenu(ligneAchat.getProduit().getDesignation()).build();
            if(notifs.stream().filter(n->n.getLigneAchat().getProduit().equals(ligneAchat.getProduit())).findAny().isEmpty()) {
                ret.add(notificationService.save(notif, ligneAchat));
            }});

      return ret;
        };


    public void sortRowsByLastTimeAdded(){

        notificationsList.sort(Comparator.comparing(Notification::getCreeLe).reversed());

    }





    public List<Notification> getAll(){
        return notificationDAO.getAll().stream().toList();
    }

}
