<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'pessoa.label', default: 'Pessoa')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-pessoa" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-pessoa" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.pessoa}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.pessoa}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.pessoa}" method="POST">
                <fieldset class="form">
                    <div class="fieldcontain required">
                        <label for="nome">Nome
                            <span class="required-indicator">*</span>
                        </label>
                        <input type="text" name="nome" value="" required="" id="nome">
                    </div>

                    <div class="fieldcontain">
                        <label for="nome">Endereco
                            <span class="required-indicator">*</span>
                        </label>
                        <g:select 
                            id="endereco" 
                            name="endereco.id" 
                            from="${grails.gorm.multitenancy.Tenants.withId(grails.gorm.multitenancy.Tenants.currentId()){project_multitenancy.Endereco.list()}}" 
                            optionKey="id" 
                            optionValue="${{it.rua+" | "+it.numero}}" 
                            value ="${this.pessoa?.endereco?.id}"/>
                    </div>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
