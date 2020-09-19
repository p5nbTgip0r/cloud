//
// MIT License
//
// Copyright (c) 2020 Alexander Söderberg & Contributors
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
package cloud.commandframework.services;

import javax.annotation.Nonnull;

/**
 * Wrapper for exceptions thrown during pipeline execution.
 *
 * @see #getCause() Use {@link #getCause()} to get the wrapped exception
 */
public final class PipelineException extends RuntimeException {

    /**
     * Construct a new pipeline exception
     *
     * @param cause Cause of the exception
     */
    public PipelineException(@Nonnull final Exception cause) {
        super(cause);
    }

    /**
     * Construct a new pipeline exception
     *
     * @param message Message explaining the exception
     * @param cause   Cause of the exception
     */
    public PipelineException(@Nonnull final String message, @Nonnull final Exception cause) {
        super(message, cause);
    }

}