package com.example.dictionary.backend;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextSpeech {

    private static boolean isAllocated = false;

    public static void read(String word)  {
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            if (!isAllocated) {
                synthesizer.allocate();
                isAllocated = true;
            }

            // Speaks the given text
            // until the queue is empty.
            synthesizer.speakPlainText(
                    word, null);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}