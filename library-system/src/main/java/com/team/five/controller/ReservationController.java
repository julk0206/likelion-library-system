package com.team.five.controller;

import com.team.five.dto.ReservationDto;
import com.team.five.dto.UsersDto;
import com.team.five.service.ReservationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/reserve/*")
public class ReservationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationService reservationService = new ReservationService();

    // 로그인 확인 공통 메서드
    private UsersDto getLoginUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false); // 세션 없으면 생성 X null 반환
        if (session == null) return null;
        return (UsersDto) session.getAttribute("loginUser");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length()).replace("/reserve", "");

        try {
            switch (command) {
                case "/list.do":
                    getReservationList(req, resp);
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
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length()).replace("/reserve", "");

        try {
            switch (command) {
                case "/insert.do":
                    insertReservation(req, resp);
                    break;
                case "/delete.do":
                    deleteReservation(req, resp);
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

    private void getReservationList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UsersDto loginUser = getLoginUser(req);
        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.do");
            return;
        }
        int userId = loginUser.getUserId();
        String userName = loginUser.getName();
        List<ReservationDto> list = reservationService.getReservationsByUserId(userId);

        req.setAttribute("reservationList", list);
        req.setAttribute("userId", userId);
        req.setAttribute("userName", userName);
        req.getRequestDispatcher("/WEB-INF/views/reserve/list.jsp").forward(req, resp);
    }

    private void insertReservation(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UsersDto loginUser = getLoginUser(req);
        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.do");
            return;
        }
        int userId = loginUser.getUserId();
        int bookId = Integer.parseInt(req.getParameter("bookId"));

        reservationService.insertReservation(userId, bookId);
        resp.sendRedirect(req.getContextPath() + "/reserve/list.do?userId=" + userId);
    }

    private void deleteReservation(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UsersDto loginUser = getLoginUser(req);
        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.do");
            return;
        }
        int userId = loginUser.getUserId();
        int reservationId = Integer.parseInt(req.getParameter("reservationId"));

        reservationService.deleteReservation(reservationId);
        resp.sendRedirect(req.getContextPath() + "/reserve/list.do?userId=" + userId);
    }
}
