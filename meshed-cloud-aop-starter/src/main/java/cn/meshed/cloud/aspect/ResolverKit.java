package cn.meshed.cloud.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Slf4j
public class ResolverKit {

    private static final String PREFIX = "#{";
    private static final String SUFFIX = "}";


    /**
     * @param contextEL           带表达式的内容
     *                            举例："用户名：+#{user.username}+，用户密码：+#{user.password}+，用户年龄：+#{user.age}+，手机号：+#{user.phone.number}"
     *                            注意：支持map对象获取key(map.key)，但是不支持在对象中嵌套map(user.map.key)
     * @param proceedingJoinPoint
     * @return
     */
    public String resolverContent(String contextEL, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            StringBuffer context = new StringBuffer();
            /**
             *  文字+#{map.name}+，+#{map.age}+#{map.abc}+.
             *  拆分
             *
             *  文字
             *  #{map.name}
             *  ，
             *  #{map.age}
             *  #{map.abc}
             *  .
             */
            if (contextEL.contains(PREFIX) && contextEL.contains(SUFFIX)) {

                List<String> strings = Arrays.asList(contextEL.split("\\+"));
                for (String s : strings) {
                    if (s.contains(PREFIX) && s.contains(SUFFIX)) {
                        // 解析表达式
                        String spel = s.substring(s.indexOf(PREFIX) + PREFIX.length(), s.lastIndexOf(SUFFIX));
                        context.append(resolverExpression(spel, proceedingJoinPoint));
                    } else {
                        // 不需要解析
                        context.append(s);
                    }
                }

            } else {
                context.append(contextEL);
            }
            return context.toString();
        } catch (Exception e) {
            log.error("表达式解析错误, 请检查!");
            return null;
        }
    }


    /**
     * 解析表达式(可嵌套两层)
     *
     * @param expression          举例： #{user.username}  #{map.key}  #{user.phone.number}
     * @param proceedingJoinPoint
     * @return
     */
    private String resolverExpression(String expression, ProceedingJoinPoint proceedingJoinPoint) throws NoSuchFieldException, IllegalAccessException {
        String first = null;
        String second = null;
        String third = null;
        if (expression.contains(".")) {
            List<String> list = Arrays.asList(expression.split("\\."));
            if (list.size() > 3) {
                throw new RuntimeException("不支持三个变量以上的表达式: " + expression);
            }
            for (int i = 0; i < list.size(); i++) {
                if (0 == i) {
                    first = list.get(i);
                }
                if (1 == i) {
                    second = list.get(i);
                }
                if (2 == i) {
                    third = list.get(i);
                }
            }
        } else {
            first = expression;
        }

        //获取参数值
        Object[] args = proceedingJoinPoint.getArgs();
        //获取运行时参数的名称
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        //获取参数值类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        //获取参数值的数据
        String[] parameterNames = new DefaultParameterNameDiscoverer().getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            String parameterName = parameterNames[i];
            Object parameterValue = args[i];

            if (first.equals(parameterName)) {
                if (null == second) {
                    return (String) parameterValue;
                }
                if (null != second) {
                    // map.name      user.username
                    if (parameterValue instanceof Map<?, ?>) {
                        return (String) ((Map) parameterValue).get(second);
                    } else {
                        return (String) getFieldValue(parameterValue, second + (null != third ? "." + third : ""));
                    }
                }
            }
        }
        return null;
    }


    /**
     * 通过反射取对象指定字段(属性)的值
     *
     * @param target    目标对象
     * @param fieldName 字段的名字
     * @return 字段的值
     */
    private Object getFieldValue(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = target.getClass();
        String[] fs = fieldName.split("\\.");

        try {
            for (int i = 0; i < fs.length - 1; i++) {
                Field f = clazz.getDeclaredField(fs[i]);
                f.setAccessible(true);
                target = f.get(target);
                if (null == target) {
                    // 嵌套内容中为null的返回null, 防止报错
                    return null;
                }
                clazz = target.getClass();
            }

            Field f = clazz.getDeclaredField(fs[fs.length - 1]);
            f.setAccessible(true);
            return f.get(target);
        } catch (Exception e) {
            throw e;
        }
    }
}

