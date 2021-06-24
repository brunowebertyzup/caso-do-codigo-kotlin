package br.com.zupacademy.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(val autorRepository: AutorRepository,
                                val enderecoClient: EnderecoClient) {

    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRequest) : HttpResponse<Any> {

        // request => dominio
        println("Requisição => $request")

        //fazer uma requisicao para um servico externo
        val enderecoResponse = enderecoClient.consulta(request.cep)

        if(enderecoResponse.body() == null) {
            return HttpResponse.badRequest()
        }

        val autor = request.paraAutor(enderecoResponse.body())
        autorRepository.save(autor)
        println("Autor => ${autor.nome}")
        var uri = UriBuilder.of("/autores/{id}")
            .expand(mutableMapOf(Pair("id", autor.id)))
        return HttpResponse.created(uri)
    }
}