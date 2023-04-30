# CS3035 – Course Project Description

## Description of My Project

My project is a simple MVC-based note keeping app that supports tagging feature. A tag or multiple tags can be added to a note. A note can be searched by the title of the note, which is the first line of the text, or the tags, which need to be comma separated when put into search bar.

I selected this as my project because I have always thought it would be useful if I could search my notes by the tags I can manually add (i.e. cs3035, exam-info)

## Requirements

- How/What different views did you provide for some aspect of your model?

My JavaFx application has a splash view, a view that creates/edits/removes note, and a view that creates/removes tag(s).

- What are the different domain objects that can be created/edited in
  your application?

List of Domain objects:
- BinderModel
- NoteModel
- TagModel

A Binder contains multiple notes, and a note contains multiple tags.

- What parts of the application/project did you find particularly challenging?

Normally, I imagine, a ListView is a list of Strings. I wanted my ListView to be a list of my domain objects such as NoteModel or TagModel. It took a while to figure out how to achieve this, but I was able to find the method I needed, which was calling setCellFactory() on my ListView object.

- Any  other comments on the project?

  Link to my video presentation: https://unbcloud-my.sharepoint.com/:v:/g/personal/qshen2_unb_ca/EZQ_bq8jVeFMv8QTF1zofiMBbgU2kBUqwmzKEv4ZtX68tA?e=WXfIGn

