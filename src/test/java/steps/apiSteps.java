package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utils.Constants;
import utils.Payload;

import static io.restassured.RestAssured.given;


public class apiSteps {
    String baseURI= RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token;
    RequestSpecification request;
    Response response;
    String employeeID;


    @Given("a JWT is generated")
    public void a_jwt_is_generated() {

                             request =given().header(Constants.Header_Content_Type, Constants.Content_Type_Value)
                                             .body(Payload.generateTokenPayload());

        Response response=request.when().post(Constants.GENERATE_TOKEN_URI); //endpoint
        String tok = response.jsonPath().getString("token");
        token="Bearar"+tok;

    }
    @Given("a request is prepared to create an Employee")
    public void a_request_is_prepared_to_create_an_employee() {
                             request=given().header(Constants.Header_Content_Type, Constants.Content_Type_Value)
                                            .header(Constants.Header_Authorization_key, token)
                                            .body(Payload.createEmployeePayload());

    }
    @When("a postcall is made to the endpoint")
    public void a_postcall_is_made_to_the_endpoint() {

        response =request.when().post(Constants.CREATE_EMPLOYEE_URI);



    }
    @Then("the status code is {int}")
    public void the_status_code_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);



    }
    @Then("the employee id {string} is stored as a global variable")
    public void the_employee_id_is_stored_as_a_global_variable(String empID) {

        employeeID=response.jsonPath().getString(empID);


    }
    @Then("we verify that the value for key {string} is {string}")
    public void we_verify_that_the_value_for_key_is(String key, String expectedValue) {
        String actualValue=response.jsonPath().getString(key);
        Assert.assertEquals(actualValue,expectedValue);


    }

}
