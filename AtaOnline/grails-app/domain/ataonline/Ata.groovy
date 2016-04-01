package ataonline

class Ata {

    String experimento
    String conteudo
    Double nota
    String arquivo
    String comentario
    


    static hasMany = [alunos: User]

    String toString(){
  		return experimento
	} 
}
