package project_multitenancy

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.gorm.multitenancy.*

@CurrentTenant
class EnderecoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [enderecoList: Endereco.list(params), enderecoCount: Endereco.count()]
    }

    def show(Long id) {
        [endereco: Endereco.get(id)]
    }

    def create() {
        [endereco: new Endereco(params)]
    }

    def save() {
        def endereco = new Endereco(params)
        try {
            endereco.save(flush: true)
        } catch (ValidationException e) {
            respond endereco.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'endereco.label', default: 'Endereco'), endereco.id])
                redirect endereco
            }
            '*' { respond endereco, [status: CREATED] }
        }
    }

    def edit(Long id) {
        def endereco = Endereco.get(id)
        [endereco: endereco]
    }

    def update(Long id) {
        def endereco = Endereco.get(id)

        try {
            endereco.save(flush: true)
        } catch (ValidationException e) {
            respond endereco.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'endereco.label', default: 'Endereco'), endereco.id])
                redirect endereco
            }
            '*'{ respond endereco, [status: OK] }
        }
    }

    def delete(Long id) {
        def endereco = Endereco.get(id)

        endereco.delete(flush: true)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'endereco.label', default: 'Endereco'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
}
