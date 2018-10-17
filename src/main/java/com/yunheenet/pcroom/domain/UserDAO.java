package com.yunheenet.pcroom.domain;

import com.yunheenet.pcroom.db.DBConnectionManager;

import java.sql.*;

public class UserDAO {
    public UserDAO () {}

    public User getUserOrNull(String id, String password) {
        Connection con = DBConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT id, name, time FROM user WHERE id=? AND password=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getString("id")
                        ,rs.getString("name")
                        ,rs.getInt("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, pstmt, con);
        }

        return null;
    }

    public void logout(String id) {
        Connection con = DBConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE user_log" +
                    " SET user_logout = NOW()" +
                    " WHERE user_id = ?" +
                    " ORDER BY user_login DESC LIMIT 1";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null, pstmt, con);
        }
    }

    public void loginSucess(String id) {
        Connection con = DBConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO user_log" +
                    "(user_id, user_login)" +
                    "VALUES(?, NOW())";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null, pstmt, con);
        }
    }

    public void updateUserTime(String id) {
        Connection con = DBConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE user" +
                    " SET time = (time-1)" +
                    " WHERE status = true" +
                    " AND id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null, pstmt, con);
        }
    }

    public User getUserInfo(String id) {
        Connection con = DBConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT name, time FROM user WHERE id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User().builder()
                        .id(id)
                        .name(rs.getString(1))
                        .time(rs.getInt(2))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, pstmt, con);
        }

        return null;
    }

    public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
}
