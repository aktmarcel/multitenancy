package project_multitenancy

import grails.gorm.MultiTenant;

class Endereco implements MultiTenant<Endereco> {
    String rua;
    Integer numero;

    static constraints = {
        rua(nullable:false);
        numero(nullable:false);
    }
}
