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

    // Interface for handling responses
    public interface ResponseCallback {
        void onSuccess(String response);
        void onFailure(Throwable throwable);
    }

    // Constructor
    public GenerativeModelHelper() {
        // Initialize the Gemini Developer API backend service
        GenerativeModel ai = FirebaseAI.getInstance(GenerativeBackend.googleAI())
                .generativeModel("gemini-2.0-flash");

        // Use the GenerativeModelFutures Java compatibility layer
        this.model = GenerativeModelFutures.from(ai);

        // Create executor for handling callbacks
        this.executor = Executors.newCachedThreadPool();
    }

    // Constructor with custom executor
    public GenerativeModelHelper(Executor customExecutor) {
        GenerativeModel ai = FirebaseAI.getInstance(GenerativeBackend.googleAI())
                .generativeModel("gemini-2.0-flash");

        this.model = GenerativeModelFutures.from(ai);
        this.executor = customExecutor;
    }

    /**
     * Generate a response for a user message
     * @param userMessage The message from the user
     * @param callback Callback to handle the response
     */
    public void generateResponse(String userMessage, ResponseCallback callback) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            callback.onFailure(new IllegalArgumentException("User message cannot be null or empty"));
            return;
        }

        // Create content with user message
        Content prompt = new Content.Builder()
                .addText(userMessage)
                .build();

        // Generate content
        ListenableFuture<GenerateContentResponse> response = model.generateContent(prompt);

        // Add callback to handle response
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
     * Generate a response synchronously (blocking call)
     * @param userMessage The message from the user
     * @return The generated response
     * @throws Exception if generation fails
     */
    public String generateResponseSync(String userMessage) throws Exception {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("User message cannot be null or empty");
        }

        Content prompt = new Content.Builder()
                .addText(userMessage)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(prompt);

        // Block and wait for result
        GenerateContentResponse result = response.get();
        return result.getText();
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
