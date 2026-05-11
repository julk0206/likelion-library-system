<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>도서 관리 시스템 - 목록</title>
    <style>
        table { width: 80%; border-collapse: collapse; margin: 20px auto; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }
        th { background-color: #f4f4f4; }
        tr:hover { background-color: #f9f9f9; }
        .search-box { text-align: center; margin-top: 30px; }
        .rank-link { display: block; text-align: right; width: 80%; margin: 0 auto; }
    </style>
</head>
<body>

    <h1 style="text-align: center;">도서 목록</h1>

    <div class="rank-link">
        <a href="${pageContext.request.contextPath}/book/rank.do">🔥 인기 도서 TOP 10 보기</a>
        &nbsp;|&nbsp;
        <a href="${pageContext.request.contextPath}/reserve/list.do">📋 예약 목록 보기</a>
    </div>

    <div class="search-box">
        <form action="${pageContext.request.contextPath}/book/list.do" method="get">
            <select name="type">
                <option value="TITLE">제목</option>
                <option value="AUTHOR">저자</option>
                <option value="ISBN">ISBN</option>
            </select>
            <input type="text" name="value" placeholder="검색어를 입력하세요">
            <button type="submit">검색</button>
        </form>
    </div>

    <table>
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>저자</th>
                <th>ISBN</th>
                <th>상세보기</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty bookList}">
                    <c:forEach var="book" items="${bookList}">
                        <tr>
                            <td>${book.bookId}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.isbn}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/book/detail.do?id=${book.bookId}">보기</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="5">조회된 도서가 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

</body>
</html>