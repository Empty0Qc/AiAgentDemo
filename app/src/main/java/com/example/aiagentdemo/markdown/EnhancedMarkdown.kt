package com.example.aiagentdemo.markdown

import android.content.Context
import io.noties.markwon.Markwon
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.latex.JLatexMathPlugin
import io.noties.markwon.syntax.SyntaxHighlightPlugin
import io.noties.markwon.simple.ext.SimpleExtPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.prism4j.Prism4j
import io.noties.prism4j.bundler.Prism4jGrammarLocatorDef
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.ext.gfm.tasklist.TaskListExtension
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.ins.InsExtension
import org.commonmark.ext.footnotes.FootnoteExtension
import org.commonmark.ext.autolink.AutolinkExtension
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension
import org.commonmark.parser.Parser

object EnhancedMarkdown {
    fun create(context: Context): Markwon {
        val prism4j = Prism4j(Prism4jGrammarLocatorDef())
        return Markwon.builder(context)
            .usePlugin(GlideImagesPlugin.create(context))
            .usePlugin(HtmlPlugin.create())
            .usePlugin(LinkifyPlugin.create())
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TaskListPlugin.create(context))
            .usePlugin(TablePlugin.create(context))
            .usePlugin(JLatexMathPlugin.create(context))
            .usePlugin(SyntaxHighlightPlugin.create(prism4j))
            .usePlugin(SimpleExtPlugin.create())
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureParser(builder: Parser.Builder) {
                    builder.extensions(
                        listOf(
                            StrikethroughExtension.create(),
                            TablesExtension.create(),
                            TaskListExtension.create(),
                            InsExtension.create(),
                            FootnoteExtension.create(),
                            AutolinkExtension.create(),
                            HeadingAnchorExtension.create()
                        )
                    )
                }
            })
            .build()
    }
}
