<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../template/header.jsp" %>


<div class="row clearfix">
    <!-- Task Info -->
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
        <div class="card">
            <div class="header">
                <h2>LISTA UŻYTKOWNIKÓW</h2>
            </div>
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-hover dashboard-task-infos">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Imię</th>
                            <th>Email</th>
                            <th>Grupa</th>
                            <th>Szczegóły</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${users}" var="user" varStatus="i">
                            <tr>
                                <td>${i.count}</td>
                                <td>${user.getUsername()}</td>
                                <td>${user.getEmail()}</td>
                                <td>${user.getUserGroup().getName()}</td>
                                <td><a href="/users/details?user_id=${user.getId()}">KLIK</a></td>
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