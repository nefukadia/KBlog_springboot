package com.kadia.kblogserber.service;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Verify;

public interface VerifyService {
    void sendVerifyAndRecord(String email) throws ServicEexception;
    Verify verifyAbleAndDelete(String verifyString) throws ServicEexception;
}
