package controller;

import dao.NhanVienDAO;
import dao.PhongBanDAO;
import model.NhanVien;
import model.PhongBan;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(urlPatterns = "/nhanvien")
public class NhanVIenServlet extends HttpServlet {
    NhanVienDAO nhanVienDAO = new NhanVienDAO();

    PhongBanDAO phongBanDAO = new PhongBanDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        RequestDispatcher dispatcher = null;
        switch (action) {
            case "create":
                req.setAttribute("phongBan", phongBanDAO.getAll());
                dispatcher = req.getRequestDispatcher("/create.jsp");
                dispatcher.forward(req, resp);
                break;
            case "search":
                String search = req.getParameter("search");
                req.setAttribute("nhanviens", nhanVienDAO.getAllByName(search));
                dispatcher = req.getRequestDispatcher("/home.jsp");
                dispatcher.forward(req, resp);
                break;
            default:
                req.setAttribute("nhanviens", nhanVienDAO.getAll());
                dispatcher = req.getRequestDispatcher("/home.jsp");
                dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        RequestDispatcher dispatcher = null;
        switch (action) {
            case "create":
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                Date birth = Date.valueOf(request.getParameter("birth"));
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                int idPB = Integer.parseInt(request.getParameter("phongban"));

                NhanVien nhanVien = new NhanVien(id, name, birth, address, phone, email, phongBanDAO.findById(idPB));
                nhanVienDAO.create(nhanVien);
                resp.sendRedirect("/nhanvien");
                break;
        }
    }
}
