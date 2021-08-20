package com.credorax.utils;

public class ThreadLocalVariables {

    private static InheritableThreadLocal<String> transactionId = new InheritableThreadLocal<>();

    public static void setTransactionId(String transactionId) {
        ThreadLocalVariables.transactionId.set(transactionId);
    }

    public static String getTransactionId() {
        return ThreadLocalVariables.transactionId.get();
    }

}
