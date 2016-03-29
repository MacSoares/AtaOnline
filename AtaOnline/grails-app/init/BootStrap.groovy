import ataonline.*

class BootStrap {

    def springSecurityService

    def init = { servletContext ->

        def adminRole = new Role('ROLE_ADMIN').save()
        def userRole = new Role('ROLE_USER').save()

        def admin = new User('admin', 'admin', '11111', 'admin@admin.ataonline', 'Administrador').save()

        UserRole.create admin, adminRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

    }
    def destroy = {
    }
}
