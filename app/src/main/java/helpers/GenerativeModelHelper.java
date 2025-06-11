package helpers;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.ai.FirebaseAI;
import com.google.firebase.ai.GenerativeModel;
import com.google.firebase.ai.java.GenerativeModelFutures;
import com.google.firebase.ai.type.Content;
import com.google.firebase.ai.type.GenerateContentResponse;
import com.google.firebase.ai.type.GenerativeBackend;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GenerativeModelHelper {

    private final GenerativeModelFutures model;
    private final Executor executor;

    public interface ResponseCallback {
        void onSuccess(String response);
        void onFailure(Throwable throwable);
    }

    // Constructor
    public GenerativeModelHelper() {
        GenerativeModel ai = FirebaseAI.getInstance(GenerativeBackend.googleAI())
                .generativeModel("gemini-2.0-flash");

        this.model = GenerativeModelFutures.from(ai);

        this.executor = Executors.newCachedThreadPool();
    }


    public void generateResponse(String userMessage, ResponseCallback callback) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            callback.onFailure(new IllegalArgumentException("User message cannot be null or empty"));
            return;
        }

        Content prompt = new Content.Builder()
                .addText(userMessage)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(prompt);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                try {
                    String resultText = result.getText();
                    callback.onSuccess(resultText);
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        }, executor);
    }


    /**
     * Generate a response with a system prompt and user message
     * @param systemPrompt Instructions for the AI
     * @param userMessage The user's message
     * @param callback Callback to handle the response
     */
    public void generateResponseWithSystem(String systemPrompt, String userMessage, ResponseCallback callback) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            callback.onFailure(new IllegalArgumentException("User message cannot be null or empty"));
            return;
        }

        // Combine system prompt and user message
        String combinedPrompt = systemPrompt != null && !systemPrompt.trim().isEmpty()
                ? systemPrompt + "\n\nUser: " + userMessage
                : userMessage;

        generateResponse(combinedPrompt, callback);
    }

    /**
     * Clean up resources
     */
    public void shutdown() {
        if (executor instanceof java.util.concurrent.ExecutorService) {
            ((java.util.concurrent.ExecutorService) executor).shutdown();
        }
    }

}
