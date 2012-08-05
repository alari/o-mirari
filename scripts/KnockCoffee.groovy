import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty

includeTargets << grailsScript("Init")
includeTargets << grailsScript("_GrailsBootstrap")
includeTargets << grailsScript("_GrailsArgParsing")

target(main: "Builds or updates .coffee ViewModels for domain classes") {
    loadApp()

    // example config:
    Map exampleConfig = [
            init: [fieldName: "in constructor thing"],
            extend: "class name to extend",
            ignore: ["field1", "field2"],
            mapping: [
                    fieldName: [
                            key: "method with o input",
                            create: "create method with o input",
                            update: "update method with o input"
                    ]
            ]
    ]

    new File("web-app/coffee/domain").mkdir()

    println "Building Coffee ViewModels..."
    for (DefaultGrailsDomainClass domainClass in grailsApp.domainClasses) {
        println "Building ${domainClass.name}VM..."

        def dCoffee = domainClass.getStaticPropertyValue("coffee", Object)
        if (false.equals(dCoffee)) continue;
        Map domainCoffee = (Map) dCoffee ?: [:]

        // Parsing domain class config
        String domainExtend = domainCoffee.extend ?: ""
        if (domainExtend) {
            domainExtend = " extends ${domainExtend}"
        }
        List domainIgnore = domainCoffee.ignore ?: []
        Map domainMapping = domainCoffee.mapping ?: [:]
        Map domainInit = domainCoffee.init ?: [:]

        // Coffee file building begins
        List<String> coffee = []
        coffee.add "exports = this"
        coffee.add("class exports.${domainClass.name}VM" + domainExtend)

        // Constructor
        coffee.add "  constructor: ->"
        if(domainExtend) coffee.add "    super()"
        coffee.add "    @id = ko.observable null"



        domainClass.persistentProperties.each {GrailsDomainClassProperty prop ->
            if (prop.name in domainIgnore) return;
            String propCoffee = "    @${prop.name} = "
            if (domainInit.get(prop.name)) {
                propCoffee += domainInit.get(prop.name)
            } else if (prop.isOneToMany() || prop.isManyToMany() || prop.isBasicCollectionType()) {
                propCoffee += "ko.observableArray []"
            } else {
                propCoffee += "ko.observable null"
            }
            coffee.add propCoffee
        }

        // Loader
        coffee.add "  fromJson: (json)=>"
        coffee.add "    mapping ="

        domainClass.persistentProperties.each {GrailsDomainClassProperty prop ->
            if (prop.name in domainIgnore) return;
            if (domainMapping.get(prop.name)) {
                Map mapping = (Map) domainMapping.get(prop.name)
                coffee.add "      ${prop.name}:"
                mapping.keySet().each {
                    coffee.add "        ${it}: (o)-> ${mapping.get(it)}"
                }
            } else if (prop.isAssociation()) {
                coffee.add "      ${prop.name}:"
                coffee.add "        create: (o)-> if o.data then new ${prop.referencedDomainClass.name}VM().fromJson(o.data) else null"
                coffee.add "        key: (o)-> ko.utils.unwrapObservable o.id"
            }
        }
        coffee.add "    ko.mapping.fromJS json, mapping, this"
        coffee.add "    this"

        new File("web-app/coffee/domain/${domainClass.name}VM.coffee").write(coffee.join("\n"))

        println()
        println coffee.join("\n")
        println("---------------------")
    }
    "coffee -o web-app/js/ --compile web-app/coffee/".execute()
    println "Built"
}

setDefaultTarget(main)
