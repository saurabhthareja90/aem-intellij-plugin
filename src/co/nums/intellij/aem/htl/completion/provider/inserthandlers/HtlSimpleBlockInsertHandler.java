package co.nums.intellij.aem.htl.completion.provider.inserthandlers;

public class HtlSimpleBlockInsertHandler extends HtlValueBlockInsertHandler {

	public static final HtlSimpleBlockInsertHandler INSTANCE = new HtlSimpleBlockInsertHandler();

	private HtlSimpleBlockInsertHandler() {
		super("=\"\"", 2);
	}

}
