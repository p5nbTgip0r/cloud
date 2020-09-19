//
// MIT License
//
// Copyright (c) 2020 Alexander Söderberg
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package com.intellectualsites.commands.arguments.standard;

import com.intellectualsites.commands.arguments.CommandArgument;
import com.intellectualsites.commands.arguments.parser.ArgumentParseResult;
import com.intellectualsites.commands.arguments.parser.ArgumentParser;
import com.intellectualsites.commands.context.CommandContext;
import com.intellectualsites.commands.exceptions.parsing.NumberParseException;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("unused")
public final class ByteArgument<C> extends CommandArgument<C, Byte> {

    private final byte min;
    private final byte max;

    private ByteArgument(final boolean required, @Nonnull final String name, final byte min,
                         final byte max, final String defaultValue) {
        super(required, name, new ByteParser<>(min, max), defaultValue, Byte.class);
        this.min = min;
        this.max = max;
    }

    /**
     * Create a new builder
     *
     * @param name Name of the argument
     * @param <C>  Command sender type
     * @return Created builder
     */
    @Nonnull
    public static <C> Builder<C> newBuilder(@Nonnull final String name) {
        return new Builder<>(name);
    }

    /**
     * Create a new required command argument
     *
     * @param name Argument name
     * @param <C>  Command sender type
     * @return Created argument
     */
    @Nonnull
    public static <C> CommandArgument<C, Byte> required(@Nonnull final String name) {
        return ByteArgument.<C>newBuilder(name).asRequired().build();
    }

    /**
     * Create a new optional command argument
     *
     * @param name Argument name
     * @param <C>  Command sender type
     * @return Created argument
     */
    @Nonnull
    public static <C> CommandArgument<C, Byte> optional(@Nonnull final String name) {
        return ByteArgument.<C>newBuilder(name).asOptional().build();
    }

    /**
     * Create a new required command argument with a default value
     *
     * @param name       Argument name
     * @param defaultNum Default num
     * @param <C>        Command sender type
     * @return Created argument
     */
    @Nonnull
    public static <C> CommandArgument<C, Byte> optional(@Nonnull final String name,
                                                        final byte defaultNum) {
        return ByteArgument.<C>newBuilder(name).asOptionalWithDefault(Byte.toString(defaultNum)).build();
    }

    /**
     * Get the minimum accepted byteeger that could have been parsed
     *
     * @return Minimum byteeger
     */
    public byte getMin() {
        return this.min;
    }

    /**
     * Get the maximum accepted byteeger that could have been parsed
     *
     * @return Maximum byteeger
     */
    public byte getMax() {
        return this.max;
    }

    public static final class Builder<C> extends CommandArgument.Builder<C, Byte> {

        private byte min = Byte.MIN_VALUE;
        private byte max = Byte.MAX_VALUE;

        protected Builder(@Nonnull final String name) {
            super(Byte.class, name);
        }

        /**
         * Set a minimum value
         *
         * @param min Minimum value
         * @return Builder instance
         */
        @Nonnull
        public Builder<C> withMin(final byte min) {
            this.min = min;
            return this;
        }

        /**
         * Set a maximum value
         *
         * @param max Maximum value
         * @return Builder instance
         */
        @Nonnull
        public Builder<C> withMax(final byte max) {
            this.max = max;
            return this;
        }

        /**
         * Builder a new byte argument
         *
         * @return Constructed argument
         */
        @Nonnull
        @Override
        public ByteArgument<C> build() {
            return new ByteArgument<>(this.isRequired(), this.getName(), this.min, this.max, this.getDefaultValue());
        }

    }

    public static final class ByteParser<C> implements ArgumentParser<C, Byte> {

        private final byte min;
        private final byte max;

        /**
         * Construct a new byte parser
         *
         * @param min Minimum value
         * @param max Maximum value
         */
        public ByteParser(final byte min, final byte max) {
            this.min = min;
            this.max = max;
        }

        @Nonnull
        @Override
        public ArgumentParseResult<Byte> parse(
                @Nonnull final CommandContext<C> commandContext,
                @Nonnull final Queue<String> inputQueue) {
            final String input = inputQueue.peek();
            if (input == null) {
                return ArgumentParseResult.failure(new NullPointerException("No input was provided"));
            }
            try {
                final byte value = Byte.parseByte(input);
                if (value < this.min || value > this.max) {
                    return ArgumentParseResult.failure(
                            new ByteParseException(input,
                                                   this.min,
                                                   this.max));
                }
                inputQueue.remove();
                return ArgumentParseResult.success(value);
            } catch (final Exception e) {
                return ArgumentParseResult.failure(
                        new ByteParseException(input, this.min,
                                               this.max));
            }
        }

        @Override
        public boolean isContextFree() {
            return true;
        }

        @Nonnull
        @Override
        public List<String> suggestions(@Nonnull final CommandContext<C> commandContext,
                                        @Nonnull final String input) {
            return IntegerArgument.IntegerParser.getSuggestions(this.min, this.max, input);
        }

    }


    /**
     * Byte parse exception
     */
    public static final class ByteParseException extends NumberParseException {

        /**
         * Construct a new byte parse exception
         *
         * @param input String input
         * @param min   Minimum value
         * @param max   Maximum value
         */
        public ByteParseException(@Nonnull final String input, final byte min, final byte max) {
            super(input, min, max);
        }

        @Override
        public boolean hasMin() {
            return this.getMin().byteValue() != Byte.MIN_VALUE;
        }

        @Override
        public boolean hasMax() {
            return this.getMax().byteValue() != Byte.MAX_VALUE;
        }

        @Override
        @Nonnull
        public String getNumberType() {
            return "byte";
        }

    }

}