package Lambda2ECSExample;

import java.util.Date;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishResult;

/**
 * Sample AWS Lambda function handler
 * @author bufalobk@
 */
public class AWSLambdaExample implements RequestHandler<String, String> {

	private final AWSCredentials credentials;

	public AWSLambdaExample() {
		this.credentials = new DefaultAWSCredentialsProviderChain().getCredentials();
	}

	public AWSLambdaExample(AWSCredentials credentials) {
		this.credentials = credentials;
	}
	
	@Override
	public String handleRequest(String input, Context context) {

		AmazonSNSClient snsCLient = new  AmazonSNSClient(credentials);
		snsCLient.setRegion(Region.US_East_2.toAWSRegion());
		CreateTopicRequest crTopicRq = new CreateTopicRequest("test");
		CreateTopicResult crTopicRp = snsCLient.createTopic(crTopicRq );

		PublishResult result = snsCLient.publish(crTopicRp.getTopicArn(), "Published from Lambda-like ECS task: \nInput: " + input + "\n" + new Date());
		
		if (result.getSdkHttpMetadata().getHttpStatusCode() == 200) {
			return "Succeeded";
		}

		return "Failed";
	}

}
