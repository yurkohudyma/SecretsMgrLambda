package ua.hudyma;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretsHandler implements RequestHandler<String, String> {

    public String handleRequest(String secretId, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Fetching secret " + secretId);

        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(null)
                .build();
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretId)
                .build();
        GetSecretValueResponse getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        client.close();
        return getSecretValueResponse.secretString();
    }
}

