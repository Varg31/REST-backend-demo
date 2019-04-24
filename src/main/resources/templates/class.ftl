<#import "parts/common.ftl" as c>

<@c.page>
    <form method="post">
        <input type="text" name="title" placeholder="Title" />

        <button type="submit">Додати</button>
    </form>

    <#list classes as class>
        <div>
            <b>${class.classId}</b>
            <span>${class.title}</span>
            <#list class.students?if_exists as student>
                <div>
                    <b>${student.studentId}</b>
                    <span>${student.name} |</span>
                    <i>${student.surname}  |</i>
                    <i>${student.middleName}  |</i>
                    <i>${student.dateOfBirth}    |</i>
                    <i>${student.gender}   |</i>
                </div>
            </#list>
        </div>
    <#else>
        No product
    </#list>
</@c.page>