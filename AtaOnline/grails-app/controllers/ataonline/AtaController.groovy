package ataonline

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured


@Transactional(readOnly = true)
class AtaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    @Secured(["ROLE_ADMIN", "ROLE_USER"])
    def index(Integer max) {
        def user = getAuthenticatedUser()
        if (user.admin) {
            render view:"menu_professores"
            params.max = Math.min(max ?: 10, 100)
            respond Ata.list(params), model:[ataCount: Ata.count()]
            System.out.println(user.admin)
        }else if(!user.admin){
            render view:"menu"
        }

    }

    def show(Ata ata) {
        respond ata
    }

    @Secured(["ROLE_ADMIN", "ROLE_USER"])
    def create() {
        respond new Ata(params)
    }

    @Transactional
    @Secured(["ROLE_ADMIN", "ROLE_USER"])
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

    @Secured("ROLE_USER")
    def edit(Ata ata) {
        respond ata
    }

    @Transactional
    @Secured("ROLE_USER")
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
    @Secured("ROLE_USER")
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
