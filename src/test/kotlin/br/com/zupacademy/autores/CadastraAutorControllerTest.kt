package br.com.zupacademy.autores

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
internal class CadastraAutorControllerTest {

    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deve cadastrar um novo autor`() {
        //cenario

        val novaAutorRequest = NovoAutorRequest(
            "Bruno Weberty",
            "bruno.weberty@zup.com.br",
            "Devagar, mas com qualidade",
            "39508-000",
            "572"
        )

        val enderecoResponse = EnderecoResponse("Rua João Luís da Silva", "Jaíba", "MG")
        Mockito.`when`(enderecoClient.consulta(novaAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        val request = HttpRequest.POST("/autores", novaAutorRequest)

        //acao
        val response = client.toBlocking().exchange(request, Any::class.java)

        //corretude
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.matches("/autores/\\d".toRegex()))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock(): EnderecoClient {
        return Mockito.mock(EnderecoClient::class.java)
    }

}