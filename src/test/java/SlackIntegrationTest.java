import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

@RunWith(JUnit4.class)
public class SlackIntegrationTest {

    private Eyes eyes;
    private static BatchInfo batch;
    private WebDriver driver;

    @BeforeClass
    public static void setBatch() {
//        System.setProperty('webdriver.chrome.driver', '/path/to/chromedriver');
        batch = new BatchInfo("Hello World");

    }

    @Before
    public void beforeEach() {
        eyes = new Eyes();
        eyes.setBatch(batch);

        // Get Applitools API key from an environment variable
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        // Create our web driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver();
    }

    @Test
    public void runTest() {

        // Open eyes to get ready to grab screenshots
        eyes.open(driver, "My Slack Integration App", "Slack Integration Test", new RectangleSize(800, 600));
        // Navigate to the page under test
        driver.get("https://applitools.com/helloworld"); // Baseline
        //driver.get("https://applitools.com/helloworld?diff1"); // Checkpoint

        // Grab screenshot and upload to Applitools
        eyes.checkWindow();
    }

    @After
    public void afterEach() {

        // Close web driver
        driver.quit();

        // Close eyes and get the TestResults Object with all the data we need
        TestResults testResults = eyes.close(false);

        // Post to results to Slack, passing the SLACK_WEBHOOK_URL we are getting from  an environment variable
//        String slack = System.getenv("SLACK_WEBHOOK_URL");

        EyesSlack.post(testResults, System.getenv("SLACK_WEBHOOK_URL"));
    }
}
