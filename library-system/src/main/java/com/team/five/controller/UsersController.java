package com.team.five.controller;

import com.team.five.dto.UsersDto;
import com.team.five.service.UsersService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/users/*")
public class UsersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsersService usersService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String command = uri.substring(req.getContextPath().length()).replace("/users", "");

        try {
            switch (command) {
                case "/login.do":
                    getLogin(req, resp);
                    break;
                case "/register.do":
                    getRegister(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String command = uri.substring(req.getContextPath().length()).replace("/users", "");

        try {
            switch (command) {
                case "/login.do":
                    handleLogin(req, resp);
                    break;
                case "/register.do":
                    insertUser(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void getLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("getLogin GET 로그인 페이지 이동");
        req.getRequestDispatcher("/WEB-INF/views/users/login.jsp").forward(req, resp);
    }

    private void getRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("getRegister GET 회원가입 페이지 이동");
        req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("handleLogin POST 로그인");

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        int userId = Integer.parseInt(req.getParameter("userId"));
        UsersDto loginUser = usersService.getUserByUserId(userId);

        if (loginUser == null) {
            req.setAttribute("errorMsg", "존재하지 않는 사용자 ID입니다.");
            req.getRequestDispatcher("/WEB-INF/views/users/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("loginUser", loginUser);
        resp.sendRedirect(req.getContextPath() + "/book/list.do");
    }

    private void insertUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("insertUser POST 회원가입");

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String name = req.getParameter("name");
        String ageParam = req.getParameter("age");
        int age = Integer.parseInt(ageParam);

        if (name == null || ageParam == null) {
            req.setAttribute("errorMsg", "이름과 나이를 모두 입력해주세요.");
            req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
            return;
        } else if (age <= 0) {
            req.setAttribute("errorMsg", "나이를 올바르게 입력해주세요.");
            req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
            return;
        }

        UsersDto newUser = new UsersDto();
        newUser.setName(name.trim());
        newUser.setAge(age);

        int result = usersService.insertUser(newUser);
        if (result == 1) {
            resp.sendRedirect(req.getContextPath() + "/users/login.do?registered=true");
        } else {
            req.setAttribute("errorMsg", "회원가입에 실패했습니다. 다시 시도해주세요.");
            req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
        }
    }
}