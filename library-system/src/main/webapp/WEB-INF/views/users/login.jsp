<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>LibrarySystem - 로그인</title>
  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; }

    body {
      font-family: 'Segoe UI', sans-serif;
      background-color: #f0f2f5;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
    }

    .card {
      background: #fff;
      width: 380px;
      border-radius: 12px;
      box-shadow: 0 4px 20px rgba(0,0,0,0.10);
      padding: 48px 40px 40px;
      text-align: center;
    }

    .logo {
      font-size: 28px;
      font-weight: 700;
      color: #1a3a5c;
      letter-spacing: -0.5px;
      margin-bottom: 6px;
    }

    .subtitle {
      font-size: 13px;
      color: #888;
      margin-bottom: 36px;
    }

    label {
      display: block;
      text-align: left;
      font-size: 13px;
      font-weight: 600;
      color: #444;
      margin-bottom: 6px;
    }

    input[type="number"] {
      width: 100%;
      padding: 11px 14px;
      border: 1px solid #d0d5dd;
      border-radius: 8px;
      font-size: 14px;
      color: #222;
      outline: none;
      transition: border 0.2s;
      margin-bottom: 20px;
    }

    input[type="number"]:focus {
      border-color: #1a3a5c;
    }

    .error-msg {
      background: #fff3f3;
      border: 1px solid #f5c2c2;
      color: #c0392b;
      font-size: 13px;
      padding: 10px 14px;
      border-radius: 6px;
      margin-bottom: 16px;
      text-align: left;
    }

    .btn-login {
      width: 100%;
      padding: 12px;
      background-color: #1a3a5c;
      color: #fff;
      border: none;
      border-radius: 8px;
      font-size: 15px;
      font-weight: 600;
      cursor: pointer;
      transition: background 0.2s;
    }

    .btn-login:hover {
      background-color: #14304f;
    }

    .footer-text {
      margin-top: 24px;
      font-size: 12px;
      color: #aaa;
    }
  </style>
</head>
<body>

<div class="card">
  <div class="logo">LibrarySystem</div>
  <div class="subtitle">도서 대여 관리 시스템</div>

  <% if (request.getAttribute("errorMsg") != null) { %>
  <div class="error-msg">${errorMsg}</div>
  <% } %>

  <form action="/users/login.do" method="post">
    <label for="userId">사용자 ID</label>
    <input type="number" id="userId" name="userId" placeholder="ID를 입력하세요" required autofocus min="1">
    <button type="submit" class="btn-login">로그인</button>
  </form>

  <p class="footer-text">© 2025 Team Five Library</p>
</div>

</body>
</html>