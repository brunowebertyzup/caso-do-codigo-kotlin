package br.com.zupacademy.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("https://viacep.com.br/ws")
interface EnderecoClient {

    // http://localhost:8081/cep/{cep}
    @Get("/{cep}/json/")
    //@Consumes(MediaType.APPLICATION_XML)
    fun consulta(@PathVariable cep: String) : HttpResponse<EnderecoResponse>
}