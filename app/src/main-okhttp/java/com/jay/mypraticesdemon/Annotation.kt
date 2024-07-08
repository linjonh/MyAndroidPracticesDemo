package com.jay.mypraticesdemon

/**
 * for test decorate
 * 用作标记反射方法 获取方法名去测试
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class MyTestMark
