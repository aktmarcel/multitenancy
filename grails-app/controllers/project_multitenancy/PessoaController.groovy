package project_multitenancy

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.gorm.multitenancy.*

@CurrentTenant
class PessoaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [pessoaList: Pessoa.list(), pessoaCount: Pessoa.count()]
    }

    def show(Long id) {
        [pessoa: Pessoa.get(id)]
    }

    def create() {
        params.enderecos = [new Endereco()]
        [pessoa: new Pessoa(params)]
    }

    def save() {
        def pessoa = new Pessoa(params)
        try {
            pessoa.save(flush: true)
        } catch (ValidationException e) {
            respond pessoa.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pessoa.label', default: 'Pessoa'), pessoa.id])
                redirect pessoa
            }
            '*' { respond pessoa, [status: CREATED] }
        }
    }

    def edit(Long id) {
        def pessoa = Pessoa.get(id)
        [pessoa: pessoa]
    }

    def update(Long id) {
        def pessoa = Pessoa.get(id)

        try {
            pessoa.save(flush: true)
        } catch (ValidationException e) {
            respond pessoa.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pessoa.label', default: 'Pessoa'), pessoa.id])
                redirect pessoa
            }
            '*'{ respond pessoa, [status: OK] }
        }
    }

    def delete(Long id) {
        def pessoa = Pessoa.get(id)

        unidadeInstance.delete(flush: true)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pessoa.label', default: 'Pessoa'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
}
