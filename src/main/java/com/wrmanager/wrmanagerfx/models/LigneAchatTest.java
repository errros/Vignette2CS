package com.wrmanager.wrmanagerfx.models;

 public class LigneAchatTest {
    private String designation;
    private String qte;
    private String prix;

     public String getDesignation() {
         return designation;
     }

     public LigneAchatTest(String designation, String qte, String prix) {
         this.designation = designation;
         this.qte = qte;
         this.prix = prix;
     }

     public void setDesignation(String designation) {
         this.designation = designation;
     }

     public String getQte() {
         return qte;
     }

     public void setQte(String qte) {
         this.qte = qte;
     }

     public String getPrix() {
         return prix;
     }

     public void setPrix(String prix) {
         this.prix = prix;
     }
 }