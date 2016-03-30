package ataonline

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import ataonline.UserRole

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	String password
	String matricula
    String email
    String nome
	boolean enabled = true
	boolean admin
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	User(String username, String password, String matricula, String email, String nome) {
		this()
		this.username = username
		this.password = password
		this.matricula = matricula
		this.email = email
		this.nome = nome
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']


	static belongsTo = Ata
    static hasMany = [atas: Ata]

	static constraints = {
		password blank: false, password: true
		username blank: false, unique: true
		atas nullable:true
	}

	static mapping = {
		password column: '`password`'
	}
}
