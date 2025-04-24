package com.example.autoservice.service;

public interface AdminService {
    /**
     * @return true, если в таблице admin_base есть запись с переданным userId
     */
    boolean isAdmin(Long userId);
}
