<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'ata.label', default: 'Ata')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
   
    <div class="nav" role="navigation">
     <ul>
      <g:each var="notas" in ="${atas}" >
        <li>${atas.nota} </li>
      </g:each>
    </ul>
            <ul>
               <li><a href="#" class="btn btn-success">Nova ata</a></li>
               <li><a href="#" class="btn btn-success">Experimentos</a></li>
               <li><a href="#" class="btn btn-success">Notas</a></li>
            </ul>
    </div>
    </body>
</html>