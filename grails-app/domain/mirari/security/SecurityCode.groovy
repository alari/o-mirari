package mirari.security

class SecurityCode {
    String token = UUID.randomUUID().toString().replaceAll("-", "");

    Account account

    Date dateCreated = new Date()

    static belongsTo = [account: Account]

    static constraints = {
        token index:true, indexAttributes: [unique:true, dropDups:true]
        account nullable: true
    }
}
