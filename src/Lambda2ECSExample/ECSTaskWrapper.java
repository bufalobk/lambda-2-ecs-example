package Lambda2ECSExample;

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

	}
}
