package br.com.zupacademy.autores

class DetalhesDoAutorResponse(//Construtor primario
    val nome: String?,
    val email: String?,
    val descricao: String?
) {
    constructor(autor: Autor) : this(//secundario
        nome = autor.nome,
        email = autor.email,
        descricao = autor.descricao
    )
}
