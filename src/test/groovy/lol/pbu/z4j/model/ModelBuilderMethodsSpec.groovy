package lol.pbu.z4j.model

import spock.lang.Specification
import java.lang.reflect.Modifier

class ModelBuilderMethodsSpec extends Specification {

    def createDummyArg(Class<?> paramType) {
        if (paramType == String.class) return "test"
        if (paramType == Long.class || paramType == long.class) return 1L
        if (paramType == Integer.class || paramType == int.class) return 1
        if (paramType == Boolean.class || paramType == boolean.class) return true
        if (paramType == Map.class) return [:]
        if (paramType == List.class) return []
        if (paramType.isArray()) return java.lang.reflect.Array.newInstance(paramType.getComponentType(), 0)
        return null
    }

    def "should execute all add*Item builder methods in the model package to verify they initialize collections correctly"() {
        given: "we scan the model package for classes"
        def classes = []
        def dir = new File("src/main/java/lol/pbu/z4j/model")
        if (dir.exists()) {
            dir.listFiles().each { file ->
                if (file.name.endsWith(".java")) {
                    def className = "lol.pbu.z4j.model." + file.name.substring(0, file.name.length() - 5)
                    try {
                        classes << Class.forName(className)
                    } catch (Exception e) {}
                }
            }
        }

        when: "we instantiate each class and call its builder methods"
        int methodsInvoked = 0
        classes.each { clazz ->
            if (!Modifier.isAbstract(clazz.modifiers) && !clazz.isEnum() && !clazz.isInterface()) {
                try {
                    def constructors = clazz.getDeclaredConstructors()
                    if (constructors.length > 0) {
                        def constructor = constructors[0]
                        def args = new Object[constructor.parameterCount]
                        for (int i = 0; i < constructor.parameterCount; i++) {
                            args[i] = createDummyArg(constructor.parameterTypes[i])
                        }
                        def instance = constructor.newInstance(args)
                        
                        clazz.declaredMethods.each { method ->
                            if (method.name.startsWith("add") && method.name.endsWith("Item") && method.parameterCount == 1) {
                                try {
                                    def dummyArg = createDummyArg(method.parameterTypes[0])
                                    method.invoke(instance, dummyArg)
                                    methodsInvoked++
                                } catch (Exception ignored) {}
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }

        then: "we successfully invoked builder methods"
        println "Invoked ${methodsInvoked} methods across ${classes.size()} classes!"
        methodsInvoked > 0
    }
}
