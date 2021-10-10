package com.example.dictionary.components;

public class WordDetailsController implements ContentController {
    @Override
    public void resetView() {
    }
    public void display(String word) {
        //TODO: Actually do something that makes sense
        System.out.println(word);
    }
}
