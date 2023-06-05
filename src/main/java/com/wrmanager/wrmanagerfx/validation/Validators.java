package com.wrmanager.wrmanagerfx.validation;

import java.sql.Date;
import java.time.LocalDate;

public class Validators{

    public static String isNomValid(String nom , Boolean required) {
        String error = "Un nom doit avoir au moins 3 characters";
        if(required || !nom.isEmpty()){
             return nom.length()>=3 ? "true"  : error;
        }
        return "true";
    }

    public static String isPrenomValid(String prenom , Boolean required) {
        String error = "Un prenom doit avoir au moins 3 characters";
        if (required || !prenom.isEmpty()) {
            return prenom.length()>=3 ? "true" : error;
        }
            return "true";
        }

        public static String isAdrValid(String adr , Boolean required) {
            String error = "Une adr doit avoir au moins 3 characters";

            if (required || !adr.isEmpty()) {
            return adr.length()>=3 ? "true" : error;
        }
            return "true";
     }

     public static String isCodeBarreValid(String codeBarre , Boolean required){
         String error = "Un Code Barre est 13 chiffres long ";

         if (required || !codeBarre.isEmpty()) {
             return codeBarre.matches("\\d{13}") ? "true" : error;
         }
         return "true";

     }

        public static String isNumValid(String num , Boolean required) {
            String error = "Un telephone doit contenir 9-10 chiffres ";

            if (required || !num.isEmpty()) {
            return num.matches("[0-9]{9,10}") ? "true" : error;
        }
            return "true";

        }

      public static String isDatePeeremptionValid(String date , Boolean required){
          String error = "La Date de peeremption doit etre une date alterieure";

          if (required || !date.isEmpty()) {

              return Date.valueOf(LocalDate.now()).before(Date.valueOf(date)) ? "true" : error;
          }
          return "true";
      }

    public static String isRemarqueValid(String remarque , Boolean required) {
        String error = "Une remarque doit avoir au moins 4 characters";

        if (required || !remarque.isEmpty()) {
            return remarque.length()>=4 ? "true" : error;
        }
        return "true";
    }

        public static String isQtyIntPositiveValid(String qty , Boolean required, Boolean acceptZero){
            String error = "La quantité doit etre un nombre rond positive";


            if (required || !qty.isEmpty()) {
                String regex = acceptZero ? "\\d{1,}(\\.0{1,3})?": "^[1-9][0-9]*([\\.\\,]0{1,3})?$";
                return qty.matches(regex) ? "true" : error;
            }

            return "true";
        }



        public static String isQtyFloatPositiveValid(String qty , Boolean required, Boolean acceptZero){
            String error = "La quantité doit etre un nombre decimal positive";


            if (required || !qty.isEmpty()) {
                String regex = acceptZero ? "^\\d{1,}(\\.\\d{1,3})?$":
                        "^(?=.*[1-9])[0-9]*.?[0-9]{1,3}$";
                return qty.matches(regex) ? "true" : error;
            }

            return "true";
        }

        //accept negative int
        public static String isQtyIntValid(String qty , Boolean required){
            String error = "La quantité doit etre un nombre rond";

            if (required || !qty.isEmpty()) {
                String regex =  "^(\\-)?\\d{1,}(\\.0{1,3})?$";
                return qty.matches(regex) ? "true" : error;
            }

            return "true";
        }




    //accept negative float
        public static String isQtyFloatValid(String qty , Boolean required){
            String error = "La quantité doit etre un nombre decimal";


            if (required || !qty.isEmpty()) {
                String regex =  "^(\\-)?\\d{1,}(\\.\\d{1,3})?$";

                return qty.matches(regex) ? "true" : error;
            }

            return "true";
        }


        //prixAchatU compared to prixVenteU
        //prixAchatU compared to prixAchatT
        //prixAchatU compared to prixVenteT
        //prixAchatT compared to prixVenteT
        //prixVenteU compared to prixVenteT


static         public String isPrixVenteGreaterThanPrixAchat(String prixAchat , String prixVente , String prixAchatT , String prixVenteT){
           String achatValidation = isQtyIntPositiveValid(prixAchat,true,true);
           if(!achatValidation.equals("true")){
               return "prix achat unitaire est un nombre rond positive";
           }
           String venteValidation = isQtyIntPositiveValid(prixVente,true,true);
           if(!achatValidation.equals("true")){
               return "prix vente unitaire doit etre un nombre rond positive";
           }
           String achatTValidation = isQtyIntPositiveValid(prixAchatT,true,true);
           if(!achatValidation.equals("true")){
               return "prix achat totale est un nombre rond positive";
           }
           String venteTValidation = isQtyIntPositiveValid(prixVente,true,true);
           if(!achatValidation.equals("true")){
               return "prix vente totale est un nombre rond positive";
           }


           Integer prixAchatU = Integer.valueOf(prixAchat);
           Integer prixAchatTotale = Integer.valueOf(prixAchatT);
           Integer prixVenteU = Integer.valueOf(prixVente);
           Integer prixVenteTotale = Integer.valueOf(prixVenteT);


           if(prixAchatU > prixVenteU){
               return "prix achat unitaire est inferieure ou egale prix vente unitaire";
           }
           if(prixAchatU >prixAchatTotale){
               return "prix achat unitaire est inferieure ou egale prix achat totale";
           }
           if(prixAchatU > prixVenteTotale){
               return "prix achat unitaire est inferieure ou egale prix vente totale";
           }
           if(prixVenteTotale < prixAchatTotale){
               return "prix achat totale est inferieure ou egale prix vente totale";
           }
           if(prixVenteTotale< prixVenteU){
               return "prix vente unitaire est inferieure ou egale prix vente totale";
           }



            return "true";


}

    }

