<#import "parts/common.ftl" as c>

<@c.page>
    <form method="post">
        <input type="text" name="name" placeholder="Name" />
        <input type="text" name="surname" placeholder="Surname">
        <input type="text" name="middleName" placeholder="Middle Name">
        <input type="text" name="dateOfBirth" placeholder="Date of birth">
        <input type="text" name="gender" placeholder="Gender">
        <button type="submit">Додати</button>
    </form>

    <#list students as student>
        <div>
            <b>${student.studentId}</b>
            <span>${student.name} |</span>
            <i>${student.surname}  |</i>
            <i>${student.middleName}  |</i>
            <i>${student.dateOfBirth}    |</i>
            <i>${student.gender}   |</i>
        </div>
        <#else>
        No product
    </#list>
</@c.page>