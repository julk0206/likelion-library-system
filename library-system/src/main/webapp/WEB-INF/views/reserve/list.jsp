<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>도서 관리 시스템 - 예약 목록</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f0f2f5;
            color: #222;
        }

        /* 네비게이션 바 */
        .navbar {
            background-color: #1a3a5c;
            padding: 0 40px;
            height: 60px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .navbar-brand {
            color: #fff;
            font-size: 20px;
            font-weight: 700;
            text-decoration: none;
            letter-spacing: -0.3px;
        }

        .navbar-right {
            display: flex;
            align-items: center;
            gap: 20px;
            font-size: 14px;
        }

        .navbar-right a {
            color: #cfe4f7;
            text-decoration: none;
            transition: color 0.2s;
        }

        .navbar-right a:hover { color: #fff; }

        .navbar-user {
            color: #fff;
            font-weight: 600;
        }

        /* 메인 컨텐츠 */
        .container {
            max-width: 900px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .page-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 24px;
        }

        .page-title {
            font-size: 22px;
            font-weight: 700;
            color: #1a3a5c;
        }

        .badge-count {
            background: #e8f0fb;
            color: #1a3a5c;
            font-size: 13px;
            font-weight: 600;
            padding: 4px 12px;
            border-radius: 20px;
        }

        /* 에러 메시지 */
        .alert-error {
            background: #fff3f3;
            border: 1px solid #f5c2c2;
            color: #c0392b;
            padding: 12px 16px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        /* 테이블 카드 */
        .table-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.07);
            overflow: hidden;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        thead tr {
            background-color: #1a3a5c;
        }

        thead th {
            color: #fff;
            padding: 14px 18px;
            text-align: center;
            font-size: 13px;
            font-weight: 600;
            letter-spacing: 0.3px;
        }

        tbody tr {
            border-bottom: 1px solid #f0f2f5;
            transition: background 0.15s;
        }

        tbody tr:last-child { border-bottom: none; }
        tbody tr:hover { background-color: #f8fafc; }

        tbody td {
            padding: 14px 18px;
            text-align: center;
            font-size: 14px;
            color: #444;
        }

        .status-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            background: #e3f2fd;
            color: #1565c0;
        }

        .empty-row td {
            padding: 60px 0;
            color: #aaa;
            font-size: 15px;
        }

        /* 취소 버튼 */
        .btn-cancel {
            background-color: #fff;
            color: #e53935;
            border: 1px solid #e53935;
            padding: 6px 16px;
            border-radius: 6px;
            font-size: 13px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.2s;
        }

        .btn-cancel:hover {
            background-color: #e53935;
            color: #fff;
        }
    </style>
</head>
<body>

<!-- 네비게이션 바 -->
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/book/list.do">LibrarySystem</a>
    <div class="navbar-right">
        <a href="${pageContext.request.contextPath}/book/list.do">도서 목록</a>
        <a href="${pageContext.request.contextPath}/book/rank.do">인기 도서</a>
        <span class="navbar-user">${userName} 님</span>
    </div>
</nav>

<!-- 메인 컨텐츠 -->
<div class="container">

    <div class="page-header">
        <h1 class="page-title">내 예약 목록</h1>
        <c:if test="${not empty reservationList}">
            <span class="badge-count">총 ${reservationList.size()}건</span>
        </c:if>
    </div>

    <!-- 에러 메시지 (중복 예약 등) -->
    <c:if test="${not empty sessionScope.errorMsg}">
        <div class="alert-error">${sessionScope.errorMsg}</div>
        <% session.removeAttribute("errorMsg"); %>
    </c:if>

    <div class="table-card">
        <table>
            <thead>
            <tr>
                <th>예약 번호</th>
                <th>도서 ID</th>
                <th>예약 일시</th>
                <th>상태</th>
                <th>예약 취소</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty reservationList}">
                    <c:forEach var="r" items="${reservationList}">
                        <tr>
                            <td>${r.reservationId}</td>
                            <td>${r.bookId}</td>
                            <td>${r.reservationDate}</td>
                            <td><span class="status-badge">${r.status}</span></td>
                            <td>
                                <c:if test="${r.status ne '대출완료'}">
                                    <form action="${pageContext.request.contextPath}/reserve/delete.do" method="post"
                                          onsubmit="return confirm('예약을 취소하시겠습니까?')">
                                        <input type="hidden" name="reservationId" value="${r.reservationId}">
                                        <input type="hidden" name="userId" value="${userId}">
                                        <button type="submit" class="btn-cancel">취소</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr class="empty-row">
                        <td colspan="5">예약 내역이 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>

</div>

</body>
</html>