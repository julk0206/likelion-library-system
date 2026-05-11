<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>LibrarySystem - 도서 대여 관리</title>
  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; }

    body {
      font-family: 'Segoe UI', sans-serif;
      background-color: #f0f2f5;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }

    header {
      background-color: #1a3a5c;
      color: #fff;
      padding: 18px 48px;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    header .brand {
      font-size: 22px;
      font-weight: 700;
      letter-spacing: -0.5px;
    }

    header .btn-login-header {
      background-color: #fff;
      color: #1a3a5c;
      border: none;
      border-radius: 8px;
      padding: 9px 22px;
      font-size: 14px;
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      transition: background 0.2s;
    }

    header .btn-login-header:hover {
      background-color: #e8edf2;
    }

    .hero {
      background: linear-gradient(135deg, #1a3a5c 0%, #2e6da4 100%);
      color: #fff;
      text-align: center;
      padding: 90px 24px 80px;
    }

    .hero h1 {
      font-size: 42px;
      font-weight: 700;
      margin-bottom: 16px;
      letter-spacing: -1px;
    }

    .hero p {
      font-size: 17px;
      color: #c5d8ed;
      margin-bottom: 40px;
    }

    .btn-start {
      display: inline-block;
      background-color: #fff;
      color: #1a3a5c;
      font-size: 16px;
      font-weight: 700;
      padding: 14px 40px;
      border-radius: 10px;
      text-decoration: none;
      transition: background 0.2s, transform 0.1s;
    }

    .btn-start:hover {
      background-color: #e8edf2;
      transform: translateY(-2px);
    }

    .features {
      display: flex;
      justify-content: center;
      gap: 28px;
      padding: 60px 48px;
      flex-wrap: wrap;
    }

    .feature-card {
      background: #fff;
      border-radius: 12px;
      box-shadow: 0 2px 12px rgba(0,0,0,0.08);
      padding: 36px 32px;
      width: 220px;
      text-align: center;
    }

    .feature-card .icon {
      font-size: 36px;
      margin-bottom: 16px;
    }

    .feature-card h3 {
      font-size: 16px;
      font-weight: 700;
      color: #1a3a5c;
      margin-bottom: 8px;
    }

    .feature-card p {
      font-size: 13px;
      color: #888;
      line-height: 1.6;
    }

    footer {
      margin-top: auto;
      text-align: center;
      padding: 24px;
      font-size: 12px;
      color: #aaa;
      background-color: #f0f2f5;
      border-top: 1px solid #e0e4e8;
    }
  </style>
</head>
<body>

<header>
  <div class="brand">LibrarySystem</div>
  <a href="/users/register.do" class="btn-login-header" style="margin-right:8px; background-color:transparent; color:#fff; border:2px solid #fff;">회원가입</a>
  <a href="/users/login.do" class="btn-login-header">로그인</a>
</header>

<div class="hero">
  <h1>도서 대여 관리 시스템</h1>
  <p>원하는 책을 검색하고, 간편하게 대여 · 예약하세요.</p>
  <a href="/users/login.do" class="btn-start">시작하기</a>
</div>

<div class="features">
  <div class="feature-card">
    <div class="icon">📚</div>
    <h3>도서 검색</h3>
    <p>보유 중인 도서를 빠르게 검색하고 상세 정보를 확인하세요.</p>
  </div>
  <div class="feature-card">
    <div class="icon">📖</div>
    <h3>도서 대여</h3>
    <p>원하는 도서를 간편하게 대여하고 반납일을 관리하세요.</p>
  </div>
  <div class="feature-card">
    <div class="icon">🔖</div>
    <h3>도서 예약</h3>
    <p>대출 중인 도서도 미리 예약해 순서를 확보하세요.</p>
  </div>
  <div class="feature-card">
    <div class="icon">📊</div>
    <h3>인기 도서</h3>
    <p>대여 횟수 기준 인기 도서 순위를 확인하세요.</p>
  </div>
</div>

<footer>© 2025 Team Five Library</footer>

</body>
</html>