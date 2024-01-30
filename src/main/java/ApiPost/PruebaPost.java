package ApiPost;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

public class PruebaPost {
	
	public static void main(String[] args) throws Exception{
        // Establecer la base URL de la API
		String apiUrl = "https://reqres.in/api/users";
        RestAssured.baseURI = apiUrl;

        // Crear un objeto JSON para enviar en la solicitud POST
        String requestBody = "{ \"nombre\": \"Prueba\", \"trabajo\": \"Automatizacion\" }";

        // Realizar la solicitud POST
        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post();

        // Imprimir el código de respuesta
        int statusCode = response.getStatusCode();
        System.out.println("Código de respuesta: " + statusCode);

        // Imprimir el cuerpo de la respuesta
        String responseBody = response.getBody().asString();
        System.out.println("Cuerpo de respuesta: " + responseBody);

        // Validar los valores en la respuesta
        Assert.assertEquals(statusCode, 201, "El código de respuesta no es el esperado.");

        Assert.assertTrue(responseBody.contains("Prueba"), "El nombre no coincide en la respuesta.");
        Assert.assertTrue(responseBody.contains("Automatizacion"), "El trabajo no coincide en la respuesta.");
        
        try {
            System.out.println("Esquema de respuesta:");
            JSONObject jsonObj = new JSONObject(responseBody);
            imprimirJSONVertical(jsonObj, 0);
        } catch (JSONException e) {
            System.out.println("Error al intentar parsear el JSON:");
            e.printStackTrace();
        }

        System.out.println("Los valores en la respuesta han sido validados con éxito.");
    }

    private static void imprimirJSONVertical(JSONObject json, int nivel) {
        for (String clave : json.keySet()) {
            Object valor = json.get(clave);

            // Agregar sangría según el nivel de anidamiento
            for (int i = 0; i < nivel; i++) {
                System.out.print("  ");
            }

            System.out.print(clave + ": ");

            if (valor instanceof JSONObject) {
                // Si el valor es otro objeto JSON, recursivamente imprimir
                System.out.println();
                imprimirJSONVertical((JSONObject) valor, nivel + 1);
            } else {
                // Si el valor es un valor simple, imprimir en la misma línea
                System.out.println(valor);
            }
        }
    }


    }

