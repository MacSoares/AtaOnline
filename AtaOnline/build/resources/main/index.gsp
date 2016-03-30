<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Ata Online</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
    <content tag="nav">
        <a href="/login/auth"><button class="btn btn-default">Login</button></a>
    </content>


    <div id="content" role="main">
        <section class="row colset-2-its">
            <h1>Hora do show porra!</h1>



            %{-- <div id="controllers" role="navigation">
                <h2>Available Controllers:</h2>
                <ul>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <li class="controller">
                            <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div> --}%
        </section>
    </div>

</body>
</html>
