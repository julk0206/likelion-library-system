<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>인기 도서 순위</title>
    <style>
        .rank-table { width: 60%; margin: 30px auto; border-collapse: collapse; }
        .rank-table th, .rank-table td { border: 1px solid #ddd; padding: 10px; text-align: center; }
        .rank-table th { background-color: #ffc107; }
        .top-3 { font-weight: bold; color: #d9534f; } /* 상위 3위 강조 */
    </style>
</head>
<body>
    <h1 style="text-align: center;">🔥 실시간 인기 도서 TOP 10</h1>
    <p style="text-align: center; color: #666;">가장 많이 대여된 도서 목록입니다.</p>

    <table class="rank-table">
        <thead>
            <tr>
                <th>순위</th>
                <th>도서명</th>
                <th>대여 횟수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="rank" items="${rankList}" varStatus="status">
                <tr class="${status.count <= 3 ? 'top-3' : ''}">
                    <td>${status.count}위</td>
                    <td>${rank.title}</td>
                    <td>${rank.rentCount}회</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div style="text-align: center;">
        <a href="list.do">전체 도서 목록으로 돌아가기</a>
    </div>
</body>
</html>