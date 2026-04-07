package com.team.five.controller;

import com.team.five.dto.BookDto;
import com.team.five.dto.response.BookRankResponse;
import com.team.five.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("*.do")
public class BookController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookService bookService = new BookService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = uri.substring(contextPath.length());

        try {
            switch (command) {
                case "/book/list.do":
                    searchBookList(request, response);
                    break;
                case "/book/detail.do":
                    searchBookDetail(request, response);
                    break;
                case "/book/rank.do":
                    searchBookRank(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void searchBookList(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String type = request.getParameter("type");
        String value = request.getParameter("value");

        List<BookDto> list;
        if (type != null && value != null) {
            list = bookService.searchBooks(type, value);
        } else {
            list = bookService.getAllBooks();
        }

        request.setAttribute("bookList", list);
        request.getRequestDispatcher("/WEB-INF/views/book/list.jsp").forward(request, response);
    }

    private void searchBookDetail(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        BookDto book = bookService.getBookDetail(bookId);

        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/views/book/detail.jsp").forward(request, response);
    }

    private void searchBookRank(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        List<BookRankResponse> rankList = bookService.getBookRank();

        request.setAttribute("rankList", rankList);
        request.getRequestDispatcher("/WEB-INF/views/book/rank.jsp").forward(request, response);
    }
}
