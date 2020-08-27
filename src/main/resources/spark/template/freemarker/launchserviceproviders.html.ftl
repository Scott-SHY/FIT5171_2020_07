<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="lsps" type="java.util.Collection<rockets.model.LaunchServiceProvider>" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Rockets: a rocket information repository</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository - Rockets">
</head>

<body>
<div id="title_pane">
    <h3>Launch Service Providers Listing Page</h3>
</div>

<div>
    <#if errorMsg?? && errorMsg?has_content>
        <li><h4 class="errorMsg">${errorMsg}</h4></li>
    <#elseif lsps?? && lsps?has_content>
        <ul>
            <#list lsps as lsp>
                <li><a href="/lsp/${lsp.id}">${lsp.name}</a></li>
            </#list>

        </ul>
        <p>Create new <a href="/lsps/create">Launch Service Provider</a></p>
    <#else>
        <p>No launch service provider yet in the system. <a href="/lsps/create">Create one</a> now!</p>
    </#if>
    <p><a href="/">Back</a></p>
</div>

</body>
</html>