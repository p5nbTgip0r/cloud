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
package cloud.commandframework.permission;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

/**
 * {@link CommandPermission} implementation backed by a {@link Predicate}
 *
 * @param <C> Command sender type
 * @since 1.4.0
 */
public class PredicatePermission<C> implements CommandPermission {

    private final Predicate<C> predicate;

    PredicatePermission(final @NonNull Predicate<C> predicate) {
        this.predicate = predicate;
    }

    /**
     * Create a new {@link PredicatePermission}
     *
     * @param predicate Predicate permission
     * @param <C>       Command sender type
     * @return Created permission
     */
    public static <C> PredicatePermission<C> of(final @NonNull Predicate<C> predicate) {
        return new PredicatePermission<>(predicate);
    }

    /**
     * Check if the given sender has the required permission
     *
     * @param sender Command sender
     * @return {@code true} if the sender has the required permission, else {@code false}
     */
    public boolean hasPermission(final C sender) {
        return this.predicate.test(sender);
    }

    @Override
    public @NonNull Collection<@NonNull CommandPermission> getPermissions() {
        return Collections.singleton(this);
    }

    @Override
    public String toString() {
        return "PredicatePermission{"
                + "predicate=" + predicate
                + '}';
    }

}
