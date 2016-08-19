package com.pinbar.springbootjwt;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    // this needs to be a DB lookup
    public boolean authenticate(Program program) {

        if (null != program && !StringUtils.isEmpty(program.getProgramName())
                && !StringUtils.isEmpty(program.getProgramPassword())) {

            String name = program.getProgramName();
            String password = program.getProgramPassword();

            if (name.equals("neo")) {
                if (password.equals("keanu")) {
                    return true;
                } else {
                    return false;
                }
            } else if (name.equals("morpheus")) {
                if (password.equals("laurence")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}