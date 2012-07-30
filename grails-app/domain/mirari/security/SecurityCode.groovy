package mirari.security

class SecurityCode {
    static mapWith = "mongo"
    String id

    String token = UUID.randomUUID().toString().replaceAll("-", "");

    Account account

    Date dateCreated

    static constraints = {
        token index:true, indexAttributes: [unique:true, dropDups:true]
        account nullable: true
    }
}
