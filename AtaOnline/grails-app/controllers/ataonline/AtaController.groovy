package ataonline

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured


@Transactional(readOnly = true)
class AtaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('ROLE_USER')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Ata.list(params), model:[ataCount: Ata.count()]
    }

    def show(Ata ata) {
        respond ata
    }

    @Secured('ADMIN_USER')
    def create() {
        respond new Ata(params)
    }

    @Transactional
    @Secured('ADMIN_USER')
    def save(Ata ata) {
        if (ata == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ata.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ata.errors, view:'create'
            return
        }

        ata.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ata.label', default: 'Ata'), ata.id])
                redirect ata
            }
            '*' { respond ata, [status: CREATED] }
        }
    }

    def edit(Ata ata) {
        respond ata
    }

    @Transactional
    def update(Ata ata) {
        if (ata == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ata.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ata.errors, view:'edit'
            return
        }

        ata.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ata.label', default: 'Ata'), ata.id])
                redirect ata
            }
            '*'{ respond ata, [status: OK] }
        }
    }

    @Transactional
    def delete(Ata ata) {

        if (ata == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ata.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ata.label', default: 'Ata'), ata.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ata.label', default: 'Ata'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
