package br.com.zupacademy.autores

import io.micronaut.http.annotation.Controller
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import javax.transaction.Transactional

@Controller("/autores")
class BuscaAutoresController(val autorRepository: AutorRepository) {

    @Get
    @Transactional
    fun lista(@QueryValue(defaultValue = "") email: String) : HttpResponse<Any> {
        if(email.isBlank()) {
            //pega do banco de dados
            val autores = autorRepository.findAll()

            //conversao para um dto saida
            val resposta = autores.map { autor -> DetalhesDoAutorResponse(autor) }

            //retornar essa lista
            return HttpResponse.ok(resposta)
        }

        val possivelAutor = autorRepository.buscarPorEmail(email)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }
        val autor = possivelAutor.get()
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }
}