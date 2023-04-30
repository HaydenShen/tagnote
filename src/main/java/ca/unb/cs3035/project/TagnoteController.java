package ca.unb.cs3035.project;

import ca.unb.cs3035.project.model.BinderModel;
import ca.unb.cs3035.project.model.NoteModel;
import ca.unb.cs3035.project.model.TagModel;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class TagnoteController {

    @FXML
    private TextArea textArea;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<NoteModel> listView;

    @FXML
    private ListView<TagModel> tagListView;

    private BinderModel binder;

    private NoteModel currentNote;

    @FXML
    private BorderPane borderPane;

    public void initialize() {
        if (!TagnoteApp.isSplashLoaded)
        {
            loadSplashScreen();
        }

        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(NoteModel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getTitle() == null) {
                    setText(null);
                } else {
                    setText(item.getTitle());
                }
            }
        });

        tagListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TagModel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getTag() == null) {
                    setText(null);
                } else {
                    setText(item.getTag());
                }
            }
        });

        binder = new BinderModel();
        addExemplaryData();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (currentNote != null)
                saveNote();
            currentNote = newValue;
            if (currentNote != null) {
                textArea.setText(currentNote.getText());
                updateTagListView(currentNote.getTags());
            }
        });
        SplitPane.setResizableWithParent(listView.getParent(), false);
    }

    public void addExemplaryData()
    {
        NoteModel note1 = new NoteModel("this is a test note");
        NoteModel note2 = new NoteModel("this is a second test note\nthis is body");

        TagModel tag1 = new TagModel("cs3035");
        TagModel tag2 = new TagModel("assignment");

        ArrayList<TagModel> tags = new ArrayList<>();

        tags.add(tag1);
        tags.add(tag2);

        note1.setTags(tags);

        binder.addNote(note1);
        binder.addNote(note2);


        updateListView(binder.getBinder());
    }
    public void addNote() {
        NoteModel note = new NoteModel("New Note");
        binder.addNote(note);
        listView.getItems().add(note);
    }

    public void deleteNote() {
        binder.removeNote(currentNote);
        listView.getItems().remove(currentNote);
    }

    public void saveNote() {
        String newText = textArea.getText();
        if (currentNote != null)
            currentNote.updateText(newText);
        listView.refresh();
    }

    public void searchNotes() {
        listView.getItems().clear();
        textArea.clear();
        tagListView.getItems().clear();
        String query = searchBar.getText();
        if (query.isEmpty()) {
            updateListView(binder.getBinder());
            return;
        }

        updateListView(binder.searchNotes(query));
    }

    public void updateListView(ArrayList<NoteModel> notes) {
        for (NoteModel note : NoteModel.getUniq(notes)) {
            listView.getItems().add(note);
        }
    }

    public void updateTagListView(ArrayList<TagModel> tags)
    {
        tagListView.getItems().clear();
        for (TagModel tag : tags) {
            tagListView.getItems().add(tag);
        }
    }


    public void editTags(ActionEvent event) {
        if (currentNote == null)
            return;

        Stage stage = TagnoteApp.getPrimaryStage();
        //stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(TagnoteApp.class.getResource("edit-tag-view.fxml"));
            Parent root = loader.load();
            EditTagController controller = loader.getController();
            controller.construct(currentNote);
            controller.setBinder(binder);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setBinder(BinderModel binder)
    {
        this.binder = binder;
        searchNotes();
    }

    @FXML void onHelpScreen() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        ImageView help = new ImageView(new Image(String.valueOf(this.getClass().getResource("help_screen.png"))));
        help.setFitHeight(400);
        help.setFitWidth(550);
        alert.setGraphic(help);
        alert.setTitle("Help Screen");
        alert.show();
    }

    @FXML void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        ImageView icon = new ImageView(new Image(String.valueOf(this.getClass().getResource("company_logo.png"))));
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        alert.getDialogPane().setGraphic(icon);
        alert.setTitle("About");
        alert.setContentText("Developed by Qunkai Shen");
        alert.show();
    }

    private void loadSplashScreen() {
        try {
            TagnoteApp.isSplashLoaded = true;
            //Load splash screen view FXML
            StackPane pane = FXMLLoader.load(getClass().getResource(("splash-view.fxml")));
            //Add it to root container (Can be StackPane, AnchorPane etc)
            borderPane.setCenter(pane);
            borderPane.setTop(new Pane());
            borderPane.setBottom(new Pane());
            borderPane.setRight(new Pane());
            borderPane.setLeft(new Pane());

            //Load splash screen with fade in effect
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            //Finish splash with fade out effect
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            //After fade in, start fade out
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            //After fade out, load actual content
            fadeOut.setOnFinished((e) -> {
                try {
                    BorderPane parentContent = FXMLLoader.load(getClass().getResource(("tagnote-view.fxml")));
                    borderPane.setTop(parentContent.getTop());
                    borderPane.setLeft(parentContent.getLeft());
                    borderPane.setRight(parentContent.getRight());
                    borderPane.setCenter(parentContent.getCenter());
                    borderPane.setBottom(parentContent.getBottom());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
