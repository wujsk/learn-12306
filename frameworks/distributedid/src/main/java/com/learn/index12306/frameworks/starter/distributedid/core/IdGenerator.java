package com.learn.index12306.frameworks.starter.distributedid.core;

/**
 * @author: cyy
 * @create: 2025-03-15 19:31
 * @description: ID生成器
 *
 * <h3>为什么要使用default方法呢</h3>
 * <p>
 * 向后兼容：在 Java 8 之前，接口中只能定义抽象方法。如果需要在现有接口中添加新方法，会导致所有实现类必须重写该方法，这可能会破坏现有的代码。
 * 使用 default 方法可以在接口中提供默认实现，而不会影响现有的实现类。
 * </p>
 * <p>
 * 减少重复代码：如果多个实现类需要相同的默认行为，可以将该行为定义在接口的 default 方法中，从而避免在每个实现类中重复编写代码
 * </p>
 * <p>
 * 提供可选功能：default 方法可以为接口提供一些可选的功能，实现类可以选择直接使用默认实现，也可以选择重写该方法以提供自定义行为。
 * </p>
 * <p>
 * 支持函数式编程：default 方法在 Java 8 的函数式接口中非常有用，例如 java.util.function 包中的接口。它们可以通过 default 方法提供一些通用的功能。
 * </p>
 **/
public interface IdGenerator {

    /**
     * 下一个 ID
     */
    default long nextId() {
        return 0L;
    }

    /**
     * 下一个 ID 字符串
     */
    default String nextStr() {
        return null;
    }
}
