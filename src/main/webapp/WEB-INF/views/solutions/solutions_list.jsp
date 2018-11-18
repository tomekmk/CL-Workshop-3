<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../template/header.jsp" %>


<div class="row clearfix">
    <!-- Task Info -->
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
        <div class="card">
            <div class="header">
                <h2>LISTA WSZYSTKICH ROZWIĄZAŃ ZADAŃ</h2>
            </div>
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-hover dashboard-task-infos">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Utworzono</th>
                            <th>Zmodyfikowano</th>
                            <th>Zadanie</th>
                            <th>Użytkownik</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${solutions}" var="solution" varStatus="i">
                            <tr>
                                <td>${i.count}</td>
                                <td>${solution.getCreated()}</td>
                                <td><c:out value="${solution.getUpdated()}" default="NIGDY"></c:out></td>
                                <td>${solution.getExercise().getTitle()}</td>
                                <td>${solution.getUser().getUsername()}</td>
                            </tr>
                        </c:forEach>

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