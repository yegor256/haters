/**
 * Copyright (c) 2016, Yegor Bugayenko
 * All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.seedramp.haters.tk.pitch;

import com.seedramp.haters.core.Base;
import com.seedramp.haters.core.Comments;
import com.seedramp.haters.core.Pitch;
import com.seedramp.haters.tk.RqAuthor;
import java.io.IOException;
import org.takes.Request;
import org.xembly.Directive;

/**
 * Pitch in the request.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class RqPitch implements Pitch {

    /**
     * The base.
     */
    private final transient Base base;

    /**
     * The request.
     */
    private final transient Request request;

    /**
     * Ctor.
     * @param bse Base
     * @param req Request
     */
    RqPitch(final Base bse, final Request req) {
        this.base = bse;
        this.request = req;
    }

    @Override
    public Comments comments() throws IOException {
        return this.pitch().comments();
    }

    @Override
    public void delete() throws IOException {
        this.pitch().delete();
    }

    @Override
    public Iterable<Directive> inXembly() throws IOException {
        return this.pitch().inXembly();
    }

    /**
     * Get pitch.
     * @return The pitch
     * @throws IOException If fails
     */
    private Pitch pitch() throws IOException {
        return new RqAuthor(this.base, this.request).pitches().pitch(
            new Path(this.request).pitch()
        );
    }

}
