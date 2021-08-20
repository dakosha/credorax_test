package com.credorax.utils;

public class ThreadLocalVariables {

    private static final InheritableThreadLocal<String> transactionId = new InheritableThreadLocal<>();

    public static String getTransactionId() {
        return ThreadLocalVariables.transactionId.get();
    }

    public static void setTransactionId(String transactionId) {
        ThreadLocalVariables.transactionId.set(transactionId);
    }

}
