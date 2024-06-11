package com.kukilabs.demoJDBC.user.entity.rowMapper;

import com.kukilabs.demoJDBC.user.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPin(rs.getInt("pin"));
        user.setLanguageId(rs.getInt("language_id"));
        user.setQuotes(rs.getString("quotes"));
        user.setProfileImgUrl(rs.getString("profile_img_url"));
        user.setGetStarted(rs.getTimestamp("get_started").toInstant());
        user.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
        user.setDeletedAt(rs.getTimestamp("deleted_at").toInstant());
        return user;
    }
}
