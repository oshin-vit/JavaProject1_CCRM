package edu.ccrm.domain;

public class MaxCreditLimitExceededException extends RuntimeException {
    public MaxCreditLimitExceededException(String msg){ super(msg); }
}
