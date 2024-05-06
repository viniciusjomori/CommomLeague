package br.edu.ifsp.commomleague.user.enums;

public enum UserStatus {
    ACTIVE,
    WAITING_EMAIL_CONFIRMING,
    BLOCKED_BY_USER,
    BLOCKED_BY_ADMIN,
    BLOCKED_BY_POLICY_TERMS,
    BLOCKED_BY_WRONG_LOGIN_ATTEMPT;
}
