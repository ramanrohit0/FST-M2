package LiveProject;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GitHubProject {
    // Declare request specification
    RequestSpecification requestSpec;
    // Declare response specification
    ResponseSpecification responseSpec;
    String sshKey;
    int sshKeyId;

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                // Set content type
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();
        sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQDFaczZM6QWrmbJfiGPiJQnJVbIAf6QwyAET/krlg7rG9E9/mKQ7Cnebg99godlu0q0fzXiv8udNLxtRqMCeN7S/7N8EKgIWVJKEkrDxnSPvCif+P23N7B5/YOhAbgQLXneBPm5XFjBolNrkJLSXDvRWygRrLzvCAbzvjRgAnitxw==";
    }

    @Test(priority=1)
    // Test case using a DataProvider
    public void addKeys() {
        String reqBody = "{\"title\": \"TestKey\", \"key\": \"" +sshKey+ "\" }";
        Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post("/user/keys"); // Send POST request

        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        sshKeyId = response.then().extract().path("id");

        // Assertions
        response.then().statusCode(201);
    }

    @Test(priority=2)
    // Test case using a DataProvider
    public void getKeys() {
        Response response = given().spec(requestSpec) // Use requestSpec
                .when().get("/user/keys"); // Send GET Request

        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);

        // Assertions
        response.then().statusCode(200);
    }

    @Test(priority=3)
    // Test case using a DataProvider
    public void deleteKeys() {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("keyId", sshKeyId).when().delete("/user/keys/{KeyId}"); // Send Delete Request

        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);

        // Assertions
        response.then().statusCode(204);
    }
}
