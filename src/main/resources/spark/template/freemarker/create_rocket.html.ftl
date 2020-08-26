<#-- @ftlvariable name="manufactuere" type="java.lang.String" -->
<#-- @ftlvariable name="country" type="java.lang.String" -->
<#-- @ftlvariable name="timeValue" type="java.lang.String" -->
<#-- @ftlvariable name="name" type="java.lang.String" -->
<#-- @ftlvariable name="firstYearFlight" type="int" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->


<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars: a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository - Create Rocket">
</head>

<body>

<div id="title_pane">
    <h3>Rocket Creation</h3>
</div>

<p>${errorMsg!""}</p>

<div>
    <p>* Fields are required.</p>
</div>
<form name="create_event" action="/rocket/create" method="POST">
    <div id="admin_left_pane" class="fieldset_without_border">
        <div><p>Mission Details</p></div>
        <ol>
            <li>
                <label for="rocketName" class="bold">Rocket Name:*</label>
                <input id="rocketName" name="rocketName" type="text" value="${name!""}">
            </li>
            <select>
                <option value ="SpaceX">SpaceX</option>
                <option value ="Sea Launch">Sea Launch</option>
            </select>
<#--            <li>-->
<#--                <label for="time" class="bold">Date and time (dd/mm/yyyy, HH AM/PM):*</label>-->
<#--                <input id="time" name="time" type="text" value="${time?datetime!""}">-->
<#--            </li>-->
            <li>
                <label for="country" class="bold">Country:*</label>
                <input id="country" name="country" type="text" value="${country!""}">
            </li>
            <li>
                <label for="firstYearFlight" class="bold">First Year Flight:</label>
                <input id="firstYearFlight" name="firstYearFlight" type="text" value="${firstYearFlight!""}">
            </li>
        </ol>
    </div>

    <#if errorMsg?? && errorMsg?has_content>
        <div id="error">
            <p>Error: ${errorMsg}</p>
        </div>
    </#if>
    <div id="buttonwrapper">
        <button type="submit">Create New Rocket</button>
        <a href="/">Cancel</a>
    </div>
</form>

</body>