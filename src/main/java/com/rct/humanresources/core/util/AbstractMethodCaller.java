package com.rct.humanresources.core.util;

import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;

@Slf4j
public abstract class AbstractMethodCaller {
    public Object callMethodByName(String methodName) {
        try {
            // Obtém a classe concreta
            Class<?> clazz = this.getClass();
            return clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
