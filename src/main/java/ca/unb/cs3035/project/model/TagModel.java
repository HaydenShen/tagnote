package ca.unb.cs3035.project.model;

import java.util.ArrayList;

public class TagModel {
    private String tag;

    public TagModel(String tag)
    {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static ArrayList<TagModel> getUniq(ArrayList<TagModel> tags) {
        ArrayList<TagModel> uniq = new ArrayList<>();
        for (TagModel tag : tags)
        {
            if (!uniq.contains(tag))
                uniq.add(tag);;
        }

        return uniq;
    }
}
