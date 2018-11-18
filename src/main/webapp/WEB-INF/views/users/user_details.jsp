<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../template/header.jsp" %>


<div class="row clearfix">
    <!-- Task Info -->
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
        <div class="card">
            <div class="header">
                <h2>UŻYTKOWNIK: ${user.getUsername()} | MAIL: ${user.getEmail()} | GRUPA: ${user.getUserGroup().getName()}</h2>
            </div>
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-hover dashboard-task-infos">
                        <thead>
                        <tr>
                            <th>Zadanie</th>
                            <th>Rozwiązania</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:if test="${not empty solutions}">
                        <c:forEach items="${solutions}" var="solution">
                            <tr>
                                <td></td>
                                <td>${solution.getDescription()}</td>
                            </tr>
                        </c:forEach>
                        </c:if>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- #END# Task Info -->


    <!-- jak niema tych divów to nie działa zmiana koloru, wtf?? -->
    <div id="donut_chart" class="dashboard-donut-chart"></div>
    <div id="real_time_chart" class="dashboard-flot-chart"></div>


</div>


<%@ include file="../template/footer.jsp" %>