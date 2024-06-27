package testando.api.task;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	private String data = "\"2025-02-15\"}";
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.log().all()
			.statusCode(200)
		;
	}
	
	@Test
	public void deveDeletarDuplicados() {
		RestAssured.given()
		.body("{ \"task\": \"Teste via API\", \"dueDate\": "+data)
		.contentType(ContentType.JSON)
	.when()
		.delete("/todo")
	.then()
		.log().all()
	;
	}
		
	@Test
	public void naodeveAdicionarTarefaDataAnterior() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"1999-06-02\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.log().all()
			.body("message", CoreMatchers.containsString("Due date must not be in past"))
		;
	}
}



