package co.nums.intellij.aem.htl.icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.Icon;

public class HtlIcons {

	public static final Icon HTL_FILE = loadIcon("htl-icon.png");
	public static final Icon HTL_BLOCK = loadIcon("htl-block.png");
	public static final Icon HTL_EXPRESSION_OPTION = loadIcon("htl-expression-option.png");
	public static final Icon HTL_DISPLAY_CONTEXT = loadIcon("htl-display-context.png");

	private HtlIcons() {
		// no instances
	}

	private static Icon loadIcon(String iconName) {
		return IconLoader.getIcon("/icons/" + iconName, HtlIcons.class);
	}

}
