import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class apiExamples {

    //STEP1.set the uri
    String baseURI= RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";

    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjY4MDE1ODgsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTcyNjg0NDc4OCwidXNlcklkIjoiNjc3MiJ9.r50ByY5iT40lSXU59Ib08xQ5rsBF9LSWjXjDkKT5tQM";


    //STEP2.write the first request
    @Test
    public void createAnEmployee(){

    //prepare the request (merr infot nga CreateAnEmployee tek postman)
         RequestSpecification request=given().header("Content-Type","application/json")
                                             .header("Authorization", token)
                                             .body("{\n" +
                                                     "  \"emp_firstname\": \"klevisa\",\n" +
                                                     "  \"emp_lastname\": \"kolaj\",\n" +
                                                     "  \"emp_middle_name\": \"ms\",\n" +
                                                     "  \"emp_gender\": \"F\",\n" +
                                                     "  \"emp_birthday\": \"1995-10-10\",\n" +
                                                     "  \"emp_status\": \"confirmed\",\n" +
                                                     "  \"emp_job_title\": \"sdet\"\n" +
                                                            "}");


          //STEP.3.send request to endpoint:

          Response response =request.when().post("/createEmployee.php");
          //response.prettyPrint(); //me kte printojm ne console pergjigjen qe presim te marrim

          String resp = response.then().extract().body().asString(); //extract the response in a form of string (means retrieving specific pieces of information from the API response (typically in JSON or XML format) after making an API request. Pra pergjigja qe pritet e create employee del ne formen e String ne console)
          System.out.println(resp);



          //extract the value of the key message
           String message = response.jsonPath().getString("Message"); //pra po kerkon te marre [ "Message": "Employee Created" ]
           Assert.assertEquals(message, "Employee Created"); //assertion [an assertion is a statement used to verify that the actual result of a test matches the expected result].



         //Assert that the employee firstName is klevisa
          String fname=response.jsonPath().getString("Employee.emp_firstname");
          Assert.assertEquals(fname, "klevisa");






    }

}
