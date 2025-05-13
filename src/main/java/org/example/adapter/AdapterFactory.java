package org.example.adapter;

import org.example.ioc.IoCContainer;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
@Component
public class AdapterFactory {
    @SuppressWarnings("unchecked")
    public static <T> T createAdapter(Class<T> iface, Object obj) {
        return (T) Proxy.newProxyInstance(
                iface.getClassLoader(),
                new Class<?>[]{iface},
                new IoCInvocationHandler(iface, obj)
        );
    }

    private static class IoCInvocationHandler implements InvocationHandler {
        private final Class<?> iface;
        private final Object obj;

        public IoCInvocationHandler(Class<?> iface, Object obj) {
            this.iface = iface;
            this.obj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            String key = iface.getName() + ":" + methodName;

            if (method.getReturnType() == Void.TYPE) {
                IoCContainer.Resolve(key, obj, args != null ? args[0] : null);
                return null;
            }

            return IoCContainer.Resolve(key, obj, args != null ? args : new Object[0]);
        }
    }
}
