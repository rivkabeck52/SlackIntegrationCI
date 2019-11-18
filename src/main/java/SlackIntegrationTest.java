import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.chrome.ChromeDriver;

public class SlackIntegrationTest {

    public static void main(String[] args) {

        // Build eyes object
        Eyes eyes = new Eyes();

        // Get Applitools API key from an environment variable
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        // Create our web driver
        ChromeDriver driver = new ChromeDriver();

        // set BatchInfo (just for the sake of the example)
        BatchInfo bi = new BatchInfo("Hello World");
        eyes.setBatch(bi);

        // Open eyes to get ready to grab screenshots
        eyes.open(driver, "My Slack Integration App", "Slack Integration Test", new RectangleSize(800, 600));
        // Navigate to the page under test
        driver.get("https://applitools.com/helloworld"); // Baseline
        //driver.get("https://applitools.com/helloworld?diff1"); // Checkpoint

        // Grab screenshot and upload to Applitools
        eyes.checkWindow();

        // Close web driver
        driver.quit();

        // Close eyes and get the TestResults Object with all the data we need
        TestResults testResults = eyes.close(false);

        // Post to results to Slack, passing the SLACK_WEBHOOK_URL we are getting from  an environment variable
//        String slack = System.getenv("SLACK_WEBHOOK_URL");
        String slack = "https://hooks.slack.com/services/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";
        EyesSlack.post(testResults , slack);
    }
}