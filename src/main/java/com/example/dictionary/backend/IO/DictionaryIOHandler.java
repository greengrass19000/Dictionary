package com.example.dictionary.backend.IO;

import com.example.dictionary.backend.TrieNode;

import java.io.FileNotFoundException;

public interface DictionaryIOHandler {
    TrieNode read(String path) throws FileNotFoundException;
    void serialize(String path, TrieNode root) throws FileNotFoundException;
}