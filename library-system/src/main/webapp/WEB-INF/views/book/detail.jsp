<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>도서 상세 정보</title>
    <style>
        .container { width: 400px; margin: 50px auto; border: 1px solid #ccc; padding: 30px; border-radius: 10px; box-shadow: 2px 2px 10px rgba(0,0,0,0.1); }
        .info-item { margin-bottom: 15px; padding-bottom: 5px; border-bottom: 1px dashed #eee; }
        .label { font-weight: bold; color: #555; width: 80px; display: inline-block; }
        .btn-group { margin-top: 20px; text-align: center; }
        button { padding: 8px 15px; cursor: pointer; border: none; border-radius: 5px; }
        .btn-back { background-color: #eee; }
        .btn-rent { background-color: #28a745; color: white; margin-left: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <h2 style="text-align: center;">📖 도서 상세 정보</h2>
        <div class="info-item"><span class="label">ID</span> ${book.bookId}</div>
        <div class="info-item"><span class="label">제목</span> ${book.title}</div>
        <div class="info-item"><span class="label">저자</span> ${book.author}</div>
        <div class="info-item"><span class="label">ISBN</span> ${book.isbn}</div>
        <div class="info-item"><span class="label">장르</span> ${book.genreCode}</div> <div class="btn-group">
            <button class="btn-back" onclick="location.href='list.do'">목록으로</button>
            <button class="btn-rent" onclick="alert('대여 기능은 구현 중입니다!')">대여하기</button>
        </div>
    </div>
    <%-- 기존 상세 정보 영역 아래에 추가 --%>
    <hr>
    <h3>📚 보유 장서 상태</h3>
    <table border="1" style="width: 100%; border-collapse: collapse; text-align: center;">
        <thead style="background-color: #f8f9fa;">
            <tr>
                <th>장서 번호</th>
                <th>상태</th>
                <th>대여하기</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty book.items}">
                    <c:forEach var="item" items="${book.items}">
                        <tr>
                            <td>${item.itemId}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.status eq '대여가능'}">
                                        <span style="color: green; font-weight: bold;">${item.status}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: red;">${item.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${item.status eq '대여가능'}">
                                    <%-- 나중에 대여 로직으로 연결될 버튼 --%>
                                    <button onclick="alert('대여 기능은 구현 중입니다!')">대여</button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="3">등록된 장서가 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>