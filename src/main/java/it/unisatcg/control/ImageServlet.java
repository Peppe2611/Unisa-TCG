package it.unisatcg.control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import it.unisatcg.model.dao.DBConnection;

@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT foto FROM prodotto WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    byte[] imgData = rs.getBytes("foto");
                    if (imgData != null) {
                        response.setContentType("image/jpeg");
                        response.getOutputStream().write(imgData);
                    }
                }
            }
        } catch (SQLException e) {
            response.sendError(500);
        }
    }
}