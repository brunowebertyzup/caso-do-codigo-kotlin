package br.com.zupacademy.autores

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import javax.transaction.Transactional

@Controller("/autores/{id}")
class AtualizaAutorController(val autorRepository: AutorRepository) {

    @Put
    @Transactional
    fun atualiza(@PathVariable id: Long, descricao: String) : HttpResponse<Any>{
        //busca o objeto no banco
        val possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }
        //atualizar o campo
        val autor = possivelAutor.get()
        autor.descricao = descricao
        autorRepository.update(autor)
        //retornar ok
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }

}