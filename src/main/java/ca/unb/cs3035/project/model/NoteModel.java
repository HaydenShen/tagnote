package ca.unb.cs3035.project.model;

import java.util.ArrayList;

public class NoteModel {
    private String title;
    private String text;
    private ArrayList<TagModel> tags;

    public NoteModel(String text)
    {
        this.text = text;
        title = this.text.split("\n", 2)[0];

        tags = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void updateText(String text) {
        this.text = text;
        title = this.text.split("\n", 2)[0];
    }

    public ArrayList<TagModel> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagModel> tags)
    {
        this.tags = tags;
    }

    public void addTad(TagModel tag)
    {
        tags.add(tag);
    }

    public void removeTag(TagModel tag)
    {
        tags.remove(tag);
    }


    public static ArrayList<NoteModel> getUniq(ArrayList<NoteModel> notes)
    {
        ArrayList<NoteModel> uniq = new ArrayList<>();
        for (NoteModel element : notes) {
            if (!uniq.contains(element)) {
                uniq.add(element);
            }
        }

        return uniq;
    }

    public boolean contains(String inputTag) {
        boolean result = false;
        for (TagModel tag : getTags()) {
            if (tag.getTag().toLowerCase().equals(inputTag.toLowerCase())) {
                result = true;
                break;
            }
        }

        return result;
    }
}
