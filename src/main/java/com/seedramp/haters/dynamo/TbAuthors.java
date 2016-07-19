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
package com.seedramp.haters.dynamo;

import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.jcabi.dynamo.Attributes;
import com.jcabi.dynamo.Item;
import com.jcabi.dynamo.QueryValve;
import com.jcabi.dynamo.Region;
import com.jcabi.dynamo.Table;
import java.io.IOException;
import java.util.Iterator;

/**
 * Dynamo table "authors".
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 1.0
 */
final class TbAuthors {

    /**
     * The region to work with.
     */
    private final transient Region region;

    /**
     * Ctor.
     * @param reg Region
     */
    TbAuthors(final Region reg) {
        this.region = reg;
    }

    /**
     * Get author points.
     * @param author Name of him
     * @return Points
     * @throws IOException If fails
     */
    public long points(final String author) throws IOException {
        return Long.parseLong(
            this.item(author).get("points").getN()
        );
    }

    /**
     * Add author points.
     * @param author Name of him
     * @param points Points to add
     * @throws IOException If fails
     */
    public void add(final String author, final long points) throws IOException {
        this.item(author).put(
            "points",
            new AttributeValueUpdate()
                .withAction(AttributeAction.ADD)
                .withValue(
                    new AttributeValue()
                        .withN(Long.toString(points))
                )
        );
    }

    /**
     * My item.
     * @return Item
     * @throws IOException If fails
     */
    private Item item(final String name) throws IOException {
        final Iterator<Item> items = this.table()
            .frame()
            .through(new QueryValve().withLimit(1))
            .where("name", name)
            .iterator();
        final Item item;
        if (items.hasNext()) {
            item = items.next();
        } else {
            item = this.table().put(
                new Attributes()
                    .with("name", name)
                    .with("points", 0)
            );
        }
        return item;
    }

    /**
     * Table to work with.
     * @return Table
     */
    private Table table() {
        return this.region.table("authors");
    }

}