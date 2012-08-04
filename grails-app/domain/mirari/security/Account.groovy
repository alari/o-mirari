package mirari.security

import mirari.Site

class Account {
	String email
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

    List<String> authorityNames

    Site mainProfile

	static constraints = {
		email blank: false, unique: true, email: true
		password blank: false
        mainProfile nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
        authorityNames.collect {new Role(authority: it)}
	}
}
