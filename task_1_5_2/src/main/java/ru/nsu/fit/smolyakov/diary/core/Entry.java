package ru.nsu.fit.smolyakov.diary.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * An entry class
 *
 * @param heading
 * @param contents
 * @param date
 */
public record Entry (
        String heading,
        String contents,
        LocalDateTime date
) {
    /**
     * Creates a new instance of {@code Entry} with a specified
     * {@code heading} and {@code contents} and current date of creation
     * (latter is provided by {@link LocalDateTime#now()}).
     *
     * @param  heading  a string literal that specifies a heading of an entry
     * @param  contents a string literal that specifies a text of an entry
     * @return a new instance of {@code Entry}
     */
    public static Entry create(String heading, String contents) {
        return new Entry(heading, contents, LocalDateTime.now());
    }

    /**
     * Returns {@code true} if this {@code Entry} heading contains
     * a specified {@code keyword}.
     *
     * @param keyword a string to be found in the heading of this {@code Entry}
     * @return {@code true} if this {@code Entry} heading contains
     *         a specified {@code keyword}.
     */
    public boolean contains(String keyword) { // TODO maybe rename maybe not
        return heading.contains(keyword);
    }

    /**
     * Returns {@code true} if this {@code Entry} was created
     * after a specified {@code date}.
     *
     * @param  date a specified {@code LocalDateTime}
     * @return {@code true} if this {@code Entry} was created
     *         after a specified {@code date}.
     */
    public boolean after(LocalDateTime date) {
        return this.date.isAfter(date);
    }

    /**
     * Returns {@code true} if this {@code Entry} was created
     * before a specified {@code date}.
     *
     * @param  date a specified {@code LocalDateTime}
     * @return {@code true} if this {@code Entry} was created
     *         before a specified {@code date}.
     */
    public boolean before(LocalDateTime date) {
        return this.date.isBefore(date);
    }
}