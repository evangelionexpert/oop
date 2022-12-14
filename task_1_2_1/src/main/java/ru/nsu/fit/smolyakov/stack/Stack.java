package ru.nsu.fit.smolyakov.stack;

import java.util.Arrays;
import java.util.Optional;

/**
 * An automatically-resizable implementation of a stack (LIFO) data structure.
 * Supports all basic stack methods, and permits all objects.
 * peek() and pop() returns Optional, so null-values are treated
 * as Optional.empty().
 * 
 * @see  Optional 
 * @see  Cloneable
 */
public class Stack<T> implements Cloneable {
    private static final int INITIAL_CAPACITY = 8;
    private static final int RESIZE_FACTOR = 2;

    private T[] arr; 
    private int size = 0;

    private void resize() {
        ensureCapacity(arr.length * RESIZE_FACTOR);
    }

    /**
     * Constructs an empty stack with the spectified initial capacity.
     * 
     * @param  capacity  the initial capacity of the stack
     * @throws IllegalArgumentException  if capacity is not a positive number
     */
    @SuppressWarnings("unchecked")
    public Stack(int capacity) throws IllegalArgumentException {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity can't be a negative number");
        }

        arr = (T[]) new Object[capacity];
    }

    /**
     * Constructs an empty stack with the initial capacity of 8.
     */
    public Stack() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a stack consisting of elements of initialArr.
     * The first element of the array will be the last 
     * element extracted from the stack.
     * 
     * @param  initialArr  an array whose elements are used 
     *                     to construct a stack
     * @throws IllegalArgumentException  if initialArr is null
     */
    public Stack(T[] initialArr) throws IllegalArgumentException {
        if (initialArr == null) {
            throw new IllegalArgumentException("Input array can't be null");
        }

        this.arr = Arrays.copyOf(initialArr, initialArr.length * RESIZE_FACTOR);
        this.size = initialArr.length;
    }

    /**
     * Increases the capacity of this {@code Stack} instance, 
     * if necessary, to ensure that it can hold at least the number 
     * of elements specified by the minimum capacity argument.
     * 
     * @param  capacity  the desired minimum capacity
     */
    public void ensureCapacity(int capacity) {
        if (arr.length < capacity) {
            arr = Arrays.copyOf(arr, capacity);
        }
    }
    
    /**
     * Returns an amount of elements in a stack.
     * 
     * @return  the amount of elements in the stack
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if a stack is empty.
     * 
     * @return  true if the stack contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Appends a single element to the top of the stack.
     * Null-values are treated as Optional.empty().
     * 
     * @param  elem  an element to push
     * @see    Optional
     */
    public void push(T elem) {
        if (size >= arr.length) {
            resize();
        }
        arr[size++] = elem;
    }     

    /**
     * Appends all elements of anotherStack to the head of a stack.
     * The last element of a stack will be followed by
     * the first element of {@code anotherStack}.
     * 
     * <p>{@code anotherStack} doesn't changed!
     * 
     * @param  anotherStack  a stack to push
     * @throws IllegalArgumentException  if anotherStack is null
     */
    public void pushStack(Stack<T> anotherStack) throws IllegalArgumentException {
        if (anotherStack == null) {
            throw new IllegalArgumentException("Stack can't be null");
        }

        ensureCapacity(this.size + anotherStack.size);
        System.arraycopy(anotherStack.arr, 0, this.arr, this.size, anotherStack.size);
        this.size += anotherStack.size;
    }
    
    /**
     * Returns a value of a top element of a stack.
     * 
     * @return  an Optional with a present value 
     *          if the stack is non-empty
     *          and the value is non-null, 
     *          otherwise an empty Optional
     * @see     Optional
     */
    public Optional<T> peek() {
        if (size > 0) {
            return Optional.ofNullable(arr[size - 1]);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Extracts a top element out of a stack.
     * 
     * @return  an Optional with a present value 
     *          if the stack is non-empty
     *          and the value is non-null, 
     *          otherwise an empty Optional
     * @see     Optional
     */
    public Optional<T> pop() {
        if (size > 0) {
            return Optional.ofNullable(arr[--size]);
        } else {
            return Optional.empty(); 
        }
    }

    /**
     * Extracts elemAmount top elements from a stack and 
     * returns a stack consisting of them. The order of
     * elements is preserved.
     * 
     * <p>If the size of the stack is less than elemAmount, 
     * the method is applied to {@link #size()} elements.
     * 
     * @param  elemAmount  an amount of elements to extract
     * @return  a stack composed of extracted elements
     * @throws IllegalArgumentException  if elemAmount is negative 
     */
    public Stack<T> popStack(int elemAmount) throws IllegalArgumentException {
        if (elemAmount < 0) {
            throw new IllegalArgumentException("You can't take negative amount of elements");
        } else if (elemAmount == 0) {
            return this.clone();
        }

        elemAmount = Math.min(size, elemAmount);

        var selectedRangeArr = Arrays.copyOfRange(arr, size - elemAmount, size);
        Arrays.fill(arr, size - elemAmount, size, null);
        size -= elemAmount; 

        return new Stack<T>(selectedRangeArr);
    }

    /**
     * Compares the specified object with this stack for equality. 
     * Returns true if and only if the specified object is also a stack, 
     * both stacks have the same size, and all corresponding pairs 
     * of elements in the two lists are equal. 
     * In other words, two stacks are defined to be equal 
     * if they contain the same elements in the same order.
     * 
     * @param  obj  an object to compare with this
     * @return      true if the specified object is equal to this stack
     */
    @Override 
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Stack<?>)) {
            return false;
        } else {
            var stack = (Stack<?>) obj;

            if (this.size != stack.size) {
                return false;
            }

            for (int i = 0; i < stack.size; i++) {
                if (!(this.arr[i] == null && stack.arr[i] == null) 
                    && !(this.arr[i] != null && this.arr[i].equals(stack.arr[i]))) {
                    return false;
                }
            }

            return true;
        }
    }
    
    /**
     * Returns a deep copy of this Stack instance.
     * 
     * @return  a deep copy of a stack
     * @see     Cloneable
     */
    public Stack<T> clone() {
        var cloned = new Stack<T>(Math.max(this.size, INITIAL_CAPACITY));
        System.arraycopy(this.arr, 0, cloned.arr, 0, this.size);
        cloned.size = this.size;

        return cloned;
    }
}