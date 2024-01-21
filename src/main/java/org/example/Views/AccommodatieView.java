package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.Controllers.AccommodatieController;
import org.example.Controllers.UserRepository;
import org.example.DBCPDataSource;
import org.example.Models.Beheerder;
import org.example.Models.iGebruiker;

import java.util.HashMap;
import java.util.Map;

public class AccommodatieView extends MenuBar {
    private Pane pane;
    private Menu menu;
    private MenuBar menuB;

    private MenuItem menuAccommodatieView, menuGebruikerView, menuZaalView, menuZaalgegevensView, menuZaallocatieView;

    private TextField txtAccommodatie;
    private Button btnOpslaan, btnUpdate, btnDelete;
    private ListView<String> lvAccommodatie;
    private AccommodatieController AccommodatieController;

    public AccommodatieView(Pane p){
        //Grid
        GridPane gridPane = new GridPane();
        AccommodatieController = new AccommodatieController();
        pane = p;
        menu = new Menu("Menu");
        iGebruiker gebruiker = UserRepository.getInstance().getGebruiker();
        System.out.println(gebruiker.getClass());

        menuAccommodatieView = new MenuItem("Accommodaties");
        menuAccommodatieView.setOnAction(actionEvent -> {
            pane.getChildren().clear();
            new AccommodatieView(p);
        });
        if(gebruiker instanceof Beheerder){
            menuGebruikerView = new MenuItem("Gebruikers");
            menuGebruikerView.setOnAction(actionEvent -> {
                pane.getChildren().clear();
                new GebruikerView(p);
            });
            menu.getItems().add(menuGebruikerView);
        }
        menuZaalView = new MenuItem("Zalen");
        menuZaalView.setOnAction(actionEvent -> {
            pane.getChildren().clear();
            new ZaalView(p);
        });
        menuZaalgegevensView = new MenuItem("Zaalgegevens");
        menuZaalgegevensView.setOnAction(actionEvent -> {
            pane.getChildren().clear();
            new ZaalgegevensView(p);
        });
        menuZaallocatieView = new MenuItem("Zaallocaties");
        menuZaallocatieView.setOnAction(actionEvent -> {
            pane.getChildren().clear();
            new ZaallocatieView(p);
        });
        //Listview
        lvAccommodatie = new ListView<>();

        //Textfields
        txtAccommodatie = new TextField();
        txtAccommodatie.setPromptText("Accommodatie");

        btnOpslaan = new Button("Voeg toe");
        btnUpdate = new Button("Bewerken");
        btnDelete = new Button("Verwijderen");

        AccommodatieController.refreshList(lvAccommodatie);


        lvAccommodatie.setOnMouseClicked(mouseEvent -> {
            AccommodatieController.fillFields(lvAccommodatie, txtAccommodatie);
        });
        btnOpslaan.setOnMouseClicked(mouseEvent -> {
            AccommodatieController.addAccommodatie(txtAccommodatie.getText());
            AccommodatieController.refreshList(lvAccommodatie);
        });
        btnUpdate.setOnMouseClicked(mouseEvent -> {
            AccommodatieController.updateAccommodatie(txtAccommodatie.getText(), lvAccommodatie.getSelectionModel().getSelectedItem());
            AccommodatieController.refreshList(lvAccommodatie);
        });
        btnDelete.setOnMouseClicked(mouseEvent -> {
            AccommodatieController.deleteAccommodatie( lvAccommodatie.getSelectionModel().getSelectedItem());
            AccommodatieController.refreshList(lvAccommodatie);
        });


        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setPadding((new Insets(15)));

        gridPane.add(txtAccommodatie,0,0);
        gridPane.add(btnOpslaan,0,1);
        gridPane.add(btnUpdate,0,2);
        gridPane.add(btnDelete,0,3);

        lvAccommodatie.relocate(300, 15);

        menu.getItems().addAll(menuAccommodatieView, menuZaalView, menuZaallocatieView, menuZaalgegevensView);
        this.getMenus().add(menu);
        p.getChildren().addAll(gridPane, lvAccommodatie);
    }
}
