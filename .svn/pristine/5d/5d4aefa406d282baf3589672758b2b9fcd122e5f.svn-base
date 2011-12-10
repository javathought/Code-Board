package ext;

/**
 * Basic groovy server page extension for markdown
 *
 * Usage: ${"**Hello** *world*".markdown().raw()}
 *
 * @author olivier refalo
 */

import java.text.ParseException;

import ext.markdown.Markdown;

import play.templates.JavaExtensions;

public class MarkdownExtensions extends JavaExtensions {

	public static String markdown(Object mdString) {

		try {
			return Markdown.transformMarkdown(mdString.toString());
		} catch (ParseException e) {
			return e.toString();
		}

	}

}
