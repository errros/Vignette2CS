package com.wrmanager.wrmanagerfx;

import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.controllers.*;
import com.wrmanager.wrmanagerfx.entities.*;
import com.wrmanager.wrmanagerfx.repositories.*;
import com.wrmanager.wrmanagerfx.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DialogPane;
import net.synedra.validatorfx.Validator;


import java.io.IOException;

//fxml paths
//files
public class Constants {


    public static ObservableList<Fournisseur> fournisseursList = FXCollections.observableArrayList();
    public static ObservableList<Commande> commandesList = FXCollections.observableArrayList();
    public static ObservableList<Produit> produitsList = FXCollections.observableArrayList();
    public static ObservableList<Achat> achatsList = FXCollections.observableArrayList();
    public static ObservableList<Vente> ventesList = FXCollections.observableArrayList();
    public static ObservableList<Categorie> categoriesList = FXCollections.observableArrayList();
    public static ObservableList<GroupeFavoris> groupeFavorisesList = FXCollections.observableArrayList();
    public static ObservableList<Vendeur> vendeursList = FXCollections.observableArrayList();
    public static ObservableList<Notification> notificationsList = FXCollections.observableArrayList();


    //Entities constants

    public static final Integer DEFAULT_MARGE_CATEGORIE = 15;
    public static final Integer DEFAULT_JOURS_ALERTE_CATEGORIE = 25;
    public static final Float DEFAULT_QTY_ALERTE_PRODUIT = 10f;
    public static final Categorie DEFAULT_CATEGORIE_PRODUIT = Categorie.builder().nom("AUTRES").build();




    //DAO dependecy injection

    public static final CategorieDAO categorieDAO = new CategorieDAO();
    public static final ProduitDAO produitDAO = new ProduitDAO();
    public static final AchatDAO achatDao = new AchatDAO();
    public static final LigneAchatDAO ligneAchatDAO = new LigneAchatDAO();
    public static final VendeurDAO vendeurDAO = new VendeurDAO();
    public static final FournisseurDAO fournisseurDAO = new FournisseurDAO();
    public static final VenteDAO venteDAO = new VenteDAO();
    public static final LigneVenteDAO ligneVenteDAO = new LigneVenteDAO();
    public static final LigneCommandeDAO ligneCommandeDAO = new LigneCommandeDAO();
    public static final CommandeDAO commandeDAO = new CommandeDAO();
    public static final ProduitFavoriDAO produitFavoriDAO = new ProduitFavoriDAO();
    public static final GroupeFavorisDAO groupeFavorisDAO = new GroupeFavorisDAO();

    public static final NotificationDAO notificationDAO = new NotificationDAO();


    public static final AchatService achatService = new AchatService();
public static final FournisseurService fournisseurService = new FournisseurService();
public static final CommandeService commandeService = new CommandeService();
public static final ProduitService produitService = new ProduitService();
public static final CategorieService categorieService = new CategorieService();
public static final FavorisService favorisService = new FavorisService();
public static final VenteService venteService = new VenteService();
public static final NotificationService notificationService = new NotificationService();


// views url
    public static final String SideBarView = "fxml/SideBar.fxml";
    public static final String VentesView = "fxml/VentesView.fxml";
    public static final String StockView = "fxml/StockView.fxml";
    public static final String AchatsView = "fxml/AchatsView.fxml";
    public static final String CommandesView = "fxml/CommandesView.fxml";
    public static final String ProduitsView = "fxml/ProduitsView.fxml";
    public static final String StatistiquesView = "fxml/StatistiquesView.fxml";
    public static final String ParametresView = "fxml/ParametresView.fxml";
    public static final String Parametres1View = "fxml/Parametres1View.fxml";
    public static final String Parametres2View = "fxml/Parametres2View.fxml";
    public static final String Parametres3View = "fxml/Parametres3View.fxml";
    public static final String FournisseursView = "fxml/FournisseursView.fxml";
    public static final String AjouterProduitDialogView = "fxml/AjouterProduitDialog.fxml";
    public static final String AjouterFournisseurDialogView = "fxml/AjouterFournisseurDialog.fxml";
    public static final String AjouterAchat1DialogView = "fxml/AjouterAchat1Dialog.fxml";
    public static final String AjouterAchat2DialogView = "fxml/AjouterAchat2Dialog.fxml";
    public static  final  String OuiNonDialogView="fxml/OuiNonDialog.fxml";
    public static  final  String AjouterCategoryDialogView="fxml/AjouterCategoryDialog.fxml";
    public static  final  String CaisseView="fxml/CaisseView.fxml";
    public static  final  String AjouterCommande1DialogView="fxml/AjouterCommande1Dialog.fxml";
    public static  final  String AjouterCommande2DialogView="fxml/AjouterCommande2Dialog.fxml";
    public static  final  String NotificationsDialogView="fxml/NotificationsView.fxml";
    public static  final  String NotificationItemView="fxml/NotificationItemView.fxml";
    public static  final  String AjouterGroupDialogView="fxml/AjouterGroupDialog.fxml";
    public static  final  String AjouterClientDialogView="fxml/AjouterClientDialog.fxml";
    public static  final  String AjouterVendeurDialogView="fxml/AjouterVendeurDialog.fxml";
    public static  final  String ImpressionCodeBarreDialogView="fxml/ImpressionCodeBarreDialog.fxml";
    public static  final  String ModifierStockDialogView="fxml/ModifierStockDialog.fxml";




    public static Parent SideBarViewLoader;
    public static Parent VentesViewLoader;
    public static Parent StockViewLoader;
    public static Parent AchatsViewLoader;
    public static Parent CommandesViewLoader;
    public static Parent ProduitsViewLoader;
    public static Parent StatistiquesViewLoader;
    public static Parent ParametresViewLoader;
    public static Parent Parametres1ViewLoader;
    public static Parent Parametres2ViewLoader;
    public static Parent Parametres3ViewLoader;
    public static Parent FournisseursViewLoader;




    public static FXMLLoader SideBarViewFxmlLoader;
    public static FXMLLoader VentesViewFxmlLoader;
    public static FXMLLoader StockViewFxmlLoader;
    public static FXMLLoader AchatsViewFxmlLoader;
    public static FXMLLoader CommandesViewFxmlLoader;
    public static FXMLLoader ProduitsViewFxmlLoader;
    public static FXMLLoader StatistiquesFxmlViewLoader;
    public static FXMLLoader ParametresFxmlViewLoader;
    public static FXMLLoader Parametres1FxmlViewLoader;
    public static FXMLLoader Parametres2FxmlViewLoader;
    public static FXMLLoader Parametres3FxmlViewLoader;
    public static FXMLLoader FournisseursFxmlViewLoader;


    public static SideBarController sideBarController;
    public static VentesViewController ventesViewController;
    public static StockViewController stockViewController;
    public static AchatsViewController achatsViewController;
    public static CommandesViewController commandesViewController;
    public static ProduitsViewController produitsViewController;
    public static StatistiquesViewController statistiquesViewController;
    public static ParametresViewController parametresViewController;
    public static Parametres1ViewController parametres1ViewController;
    public static Parametres2ViewController parametres2ViewController;
    public static Parametres3ViewController parametres3ViewController;
    public static FournisseursViewController fournisseursViewController;

    static public void initObservableLists(){


        achatsList.addAll(achatService.getAll());
        achatService.sortRowsByLastTimeAdded();

        fournisseursList.addAll(fournisseurService.getAll());
        fournisseurService.sortRowsByLastTimeAdded();

        produitsList.addAll(produitService.getAll());
        produitService.sortRowsByLastTimeAdded();

        categoriesList.addAll(categorieService.getAll());
        categorieService.sortRowsByLastTimeAdded();

        groupeFavorisesList.addAll(favorisService.getAll());
        favorisService.sortRowsByLastTimeAdded();

        commandesList.addAll(commandeService.getAll());
        commandeService.sortRowsByLastTimeAdded();

        ventesList.addAll(venteService.getAll());
        venteService.sortRowsByLastTimeAdded();

        notificationsList.addAll(notificationService.getAll());
        notificationService.sortRowsByLastTimeAdded();





    }



    static public void  load(){
        try {

             SideBarViewFxmlLoader=new FXMLLoader(Main.class.getResource(Constants.SideBarView));
             VentesViewFxmlLoader=new FXMLLoader(Main.class.getResource(Constants.VentesView));
             StockViewFxmlLoader=new FXMLLoader(Main.class.getResource(Constants.StockView));
             AchatsViewFxmlLoader=new FXMLLoader(Main.class.getResource(Constants.AchatsView));
             CommandesViewFxmlLoader=new FXMLLoader(Main.class.getResource(Constants.CommandesView));
             ProduitsViewFxmlLoader=new FXMLLoader(Main.class.getResource(Constants.ProduitsView));
             StatistiquesFxmlViewLoader=new FXMLLoader(Main.class.getResource(Constants.StatistiquesView));
             ParametresFxmlViewLoader=new FXMLLoader(Main.class.getResource(Constants.ParametresView));
             Parametres1FxmlViewLoader=new FXMLLoader(Main.class.getResource(Constants.Parametres1View));
             Parametres2FxmlViewLoader=new FXMLLoader(Main.class.getResource(Constants.Parametres2View));
             Parametres3FxmlViewLoader=new FXMLLoader(Main.class.getResource(Constants.Parametres3View));
             FournisseursFxmlViewLoader=new FXMLLoader(Main.class.getResource(Constants.FournisseursView));






            VentesViewLoader=VentesViewFxmlLoader.load();
            StockViewLoader=StockViewFxmlLoader.load();
            AchatsViewLoader=AchatsViewFxmlLoader.load();
            CommandesViewLoader=CommandesViewFxmlLoader.load();
            ProduitsViewLoader=ProduitsViewFxmlLoader.load();
            Parametres1ViewLoader=Parametres1FxmlViewLoader.load();
            Parametres2ViewLoader=Parametres2FxmlViewLoader.load();
            Parametres3ViewLoader=Parametres3FxmlViewLoader.load();
            ParametresViewLoader=ParametresFxmlViewLoader.load();
            FournisseursViewLoader=FournisseursFxmlViewLoader.load();
            StatistiquesViewLoader=StatistiquesFxmlViewLoader.load();

            sideBarController= SideBarViewFxmlLoader.getController();
            ventesViewController=VentesViewFxmlLoader.getController();
            stockViewController=StockViewFxmlLoader.getController();
            achatsViewController=AchatsViewFxmlLoader.getController();
            commandesViewController=CommandesViewFxmlLoader.getController();
            produitsViewController=ProduitsViewFxmlLoader.getController();
            statistiquesViewController=StatistiquesFxmlViewLoader.getController();
            parametresViewController=ParametresFxmlViewLoader.getController();
            parametres1ViewController =Parametres1FxmlViewLoader.getController();
            parametres2ViewController=Parametres2FxmlViewLoader.getController();
            parametres3ViewController=Parametres3FxmlViewLoader.getController();
            fournisseursViewController=FournisseursFxmlViewLoader.getController();


            //System.out.println(StatistiquesView);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
