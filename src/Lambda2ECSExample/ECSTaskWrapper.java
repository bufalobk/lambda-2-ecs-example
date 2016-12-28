package Lambda2ECSExample;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.util.EC2MetadataUtils;

/**
 * Sample wrapper for AWS Lambda function in Java.
 * 
 * @author bufalobk
 */
public class ECSTaskWrapper {
	public static void main(String[] args) {

		String input = System.getenv("LAMBDA_INPUT");

		if (input == null || input.length() == 0) {
			System.out.println("Not being called from an AWS Lambda function.");
			System.exit(1);
		}

		try {
			AWSLambdaExample lde = new AWSLambdaExample();
			System.out.println(lde.handleRequest(input, null));
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean isDedicatedInstance = Boolean.parseBoolean(System.getenv("DEDICATED_ECS_INSTANCE"));

		if (isDedicatedInstance) {
			System.out.println("Terminating EC2 instance...");
			AmazonEC2Client ec2Client = new AmazonEC2Client(new DefaultAWSCredentialsProviderChain().getCredentials());
			ec2Client.setRegion(com.amazonaws.regions.Regions.getCurrentRegion());
			TerminateInstancesRequest terminateRq = new TerminateInstancesRequest();
			terminateRq.withInstanceIds(EC2MetadataUtils.getInstanceId());
			ec2Client.terminateInstances(terminateRq);
		}
	}
}
