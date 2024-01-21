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
    private Label lblAccommodatie;
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

        menuAccommodatieView = new MenuItem("Accommodaties");
        menuAccommodatieView.setOnAction(actionEvent -> {
            pane.getChildren().clear();
            new AccommodatieView(p);
        });

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

        //Buttons
        btnOpslaan = new Button("Voeg toe");
        btnUpdate = new Button("Bewerken");
        btnDelete = new Button("Verwijderen");

        //Labels
        lblAccommodatie = new Label("Accommodatienaam");

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

        gridPane.add(lblAccommodatie,0,0);
        gridPane.add(txtAccommodatie,0,1);
        gridPane.add(btnOpslaan,0,2);
        if(gebruiker instanceof Beheerder) {
            gridPane.add(btnUpdate,0,3);
            gridPane.add(btnDelete,0,4);
        }

        //Button width (gelijk aan TextFields)
        btnOpslaan.setMinWidth(187);
        btnUpdate.setMinWidth(187);
        btnDelete.setMinWidth(187);

        //TextField width
        txtAccommodatie.setMinWidth(187);

        lvAccommodatie.relocate(300, 15);

        menu.getItems().addAll(menuAccommodatieView, menuZaalView, menuZaallocatieView, menuZaalgegevensView);

        if(gebruiker instanceof Beheerder){
            menuGebruikerView = new MenuItem("Gebruikers");
            menuGebruikerView.setOnAction(actionEvent -> {
                pane.getChildren().clear();
                new GebruikerView(p);
            });
            menu.getItems().add(menuGebruikerView);
        }

        this.getMenus().add(menu);
        p.getChildren().addAll(gridPane, lvAccommodatie);
    }
}
