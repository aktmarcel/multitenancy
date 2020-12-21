package project_multitenancy

import grails.gorm.MultiTenant;

class Pessoa implements MultiTenant<Pessoa>{
    String nome
    Endereco endereco

    static constraints = {
        nome(nullable:false);
        endereco(nullable:true);
    }

    static mapping = {
        enderecos cascade:"all-delete-orphan"
    }
}
