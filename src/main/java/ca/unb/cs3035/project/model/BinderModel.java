package ca.unb.cs3035.project.model;

import java.util.ArrayList;
import java.util.Arrays;

public class BinderModel {
    private ArrayList<NoteModel> binder;

    public BinderModel() {
        binder = new ArrayList<>();
    }

    public void addNote(NoteModel note) {
        binder.add(note);
    }

    public void removeNote(NoteModel note) {
        binder.remove(note);
    }

    public ArrayList<NoteModel> getBinder() {
        return binder;
    }

    public ArrayList<NoteModel> searchNotes(String query) {

        ArrayList<NoteModel> result = new ArrayList<>();

        ArrayList<String> queriedTags = new ArrayList<>(Arrays.asList(query.split(",")));

        boolean isAdded;
        for (NoteModel note : getBinder())
        {
            isAdded = false;
            String title = note.getTitle();
            if (title.toLowerCase().contains(query.toLowerCase()))
            {
                result.add(note);
            }
            for (TagModel tag : note.getTags())
            {
                for (String queriedTag : queriedTags)
                {
                    if (tag.getTag().toLowerCase().matches(queriedTag.toLowerCase()))
                    {
                        result.add(note);
                        isAdded = true;
                        break;
                    }
                }
                if (isAdded)
                    break;
            }
        }
        return result;
    }
}
