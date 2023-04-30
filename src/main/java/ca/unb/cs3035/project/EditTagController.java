package ca.unb.cs3035.project;

import ca.unb.cs3035.project.model.BinderModel;
import ca.unb.cs3035.project.model.NoteModel;
import ca.unb.cs3035.project.model.TagModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditTagController {
    @FXML
    private TextArea textArea;
    @FXML
    private ListView<TagModel> listView;

    @FXML
    private TextField textField;

    @FXML
    private Label message;

    private NoteModel currentNote;

    private TagModel currentTag;

    private BinderModel binder;

    @FXML
    BorderPane borderPane;

    public void initialize() {
        listView.setCellFactory(param -> new ListCell<>() {
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

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //if (currentTag != null)
                //saveNote();
            currentTag = newValue;
        });

    }

    public void construct(NoteModel note)
    {
        currentNote = note;
        setTextArea(currentNote);
        setListView(currentNote);
    }

    public void setBinder(BinderModel binder)
    {
        this.binder = binder;
    }

    public void setTextArea(NoteModel note)
    {
        String text = note.getText();
        textArea.setText(text);
    }

    public void setListView(NoteModel note)
    {
        ArrayList<TagModel> tags = note.getTags();
        for (TagModel tag : tags) {
            listView.getItems().add(tag);
        }
    }

    public void completeEdit(ActionEvent event)
    {
        Stage stage = TagnoteApp.getPrimaryStage();

        try {
            FXMLLoader loader = new FXMLLoader(TagnoteApp.class.getResource("tagnote-view.fxml"));
            Parent root = loader.load();
            TagnoteController controller = loader.getController();
            controller.setBinder(binder);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addTag() {
        String newTag = textField.getText();
        if (newTag.isEmpty() || currentNote.contains(newTag))
        {
            message.setText("Error: tag already exist");
            return;
        }
        TagModel tag = new TagModel(newTag);
        currentNote.addTad(tag);
        listView.getItems().add(tag);
        message.setText("");
    }

    public void removeTag() {
        currentNote.removeTag(currentTag);
        listView.getItems().remove(currentTag);
    }
}
