package com.unburden.functest

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonassert.JsonAssert
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.restassured.RestAssured
import io.restassured.response.Response
import jakarta.ws.rs.core.MediaType
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.Assert

class HttpCommonDefs {
    private var result: Response? = null
    private var userToken: String? = null

    @Then("I store user token from response$")
    fun storeUserToken() {
        val readValue = ObjectMapper().readValue(result!!.body().print(), Map::class.java)
        userToken = readValue["userToken"].toString()
    }

    @Given("I GET to uri {string}")
    fun get(path: String?) {
        val token = data.get()
        result = if (token != null) {
            RestAssured
                .given()
                .header("Authorization", "Bearer $token")
                .`when`()
                .contentType(MediaType.APPLICATION_JSON)
                .get(path)
        } else {
            RestAssured.given().`when`().contentType(MediaType.APPLICATION_JSON).get(path)
        }
    }

    @Given("I GET to uri {string} with user token")
    fun getWithUserToken(path: String?) {
        result = RestAssured
                .given()
                .header("Authorization", "Bearer $userToken")
                .`when`()
                .contentType(MediaType.APPLICATION_JSON)
                .get(path)
    }

    @Given("I POST to uri {string} with body")
    fun post(path: String?, body: String?) {
        val token = data.get()
        if (token != null) {
            result = RestAssured
                .given()
                .header("Authorization", "Bearer $token")
                .`when`()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(body)
                .post(path)
        } else {
            result = RestAssured.given()
                .`when`()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(body)
                .post(path)
        }
    }

    @Given("I PUT to uri {string} with body")
    fun put(path: String?, body: String?) {
        val token = data.get()
        if (token != null) {
            result = RestAssured
                .given()
                .header("Authorization", "Bearer $token")
                .`when`()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(body)
                .put(path)
        } else {
            result = RestAssured.given()
                .`when`()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(body)
                .put(path)
        }
    }

    @Then("the response code is {int}")
    fun assertThatResponseCodeIs(expectedStatusCode: Int) {
        Assert.assertEquals(expectedStatusCode.toLong(), result!!.statusCode().toLong())
    }

    @Then("^the response matches$")
    fun assertThatResponseJsonMatches(assertions: List<Map<String?, String?>>) {
        val asserter = JsonAssert.with(result!!.body().print())
        assertions.forEach { a ->
            val path = a["path"]
            val matcher = a["matcher"]
            val expected = a["expected"]
            when (matcher) {
                "isInt" -> asserter.assertThat(path, CoreMatchers.`is`(Integer.valueOf(expected)))
                "isNotNull" -> asserter.assertThat(path, Matchers.notNullValue())
                "isNotPresent" -> asserter.assertNotDefined("path")
                "isBoolean" -> asserter.assertEquals(path, expected.toBoolean())
                "contains" -> asserter.assertThat(path, CoreMatchers.containsString(expected))
                else -> asserter.assertThat(path, CoreMatchers.`is`(expected))
            }
        }
    }

    companion object {
        val data = ThreadLocal<String>()
    }
}